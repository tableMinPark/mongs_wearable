package com.mongs.wear.data.common.api

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.mongs.wear.data.R
import com.mongs.wear.data.common.consumer.MqttConsumer
import info.mqtt.android.service.MqttAndroidClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class MqttApi (
    private val context: Context,
    private val mqttAndroidClient: MqttAndroidClient,
    private val mqttConsumer: MqttConsumer,
    private val gson: Gson,
) {
    companion object {
        private var connectPending = false
    }

    fun isConnected(): Boolean = mqttAndroidClient.isConnected

    fun isConnectPending(): Boolean = connectPending

    suspend fun connect() {
//        withContext(Dispatchers.IO) {
        connectPending = true
            val options = MqttConnectOptions().apply {
                this.userName = context.getString(R.string.mqtt_username)
                this.password = context.getString(R.string.mqtt_password).toCharArray()
            }
            mqttAndroidClient.setCallback(mqttConsumer)
            mqttAndroidClient.connect(options).await()
            Log.i("MqttClientApi", "connect.")
        connectPending = false
//        }
    }

    suspend fun disConnect() {
//        withContext(Dispatchers.IO) {
            if (mqttAndroidClient.isConnected) {
                mqttAndroidClient.disconnect().await()
                Log.i("MqttClientApi", "disConnect.")
            }
//        }
    }

    suspend fun subscribe(topic: String) {
//        withContext(Dispatchers.IO) {
            if (mqttAndroidClient.isConnected) {
                mqttAndroidClient.subscribe(topic, 2).await()
                Log.i("MqttClientApi", "[$topic] subscribe.")
            } else {
                Log.e("MqttClientApi", "[$topic] subscribe fail.")
            }
//        }
    }

    suspend fun disSubscribe(topic: String) {
//        withContext(Dispatchers.IO) {
            if (mqttAndroidClient.isConnected) {
                mqttAndroidClient.unsubscribe(topic).await()
                Log.i("MqttClientApi", "[$topic] unSubscribe.")
            } else {
                Log.e("MqttClientApi", "[$topic] unSubscribe fail.")
            }
//        }
    }

    suspend fun <T> produce(topic: String, requestDto: T) {
//        withContext(Dispatchers.IO) {
            if (mqttAndroidClient.isConnected) {

                val payload = gson.toJson(requestDto).toByteArray()

                mqttAndroidClient.publish(topic = topic, payload = payload, qos = 1, retained = true).await()

                Log.i("MqttClientApi", "[$topic] : $payload")
            } else {
                Log.e("MqttClientApi", "[$topic] produce fail.")
            }
//        }
    }

    private suspend fun IMqttToken.await() = suspendCancellableCoroutine { cont ->
        actionCallback = object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                cont.resume(Unit)
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                exception?.printStackTrace()
                cont.resumeWithException(exception ?: Exception("Unknown error"))
            }
        }
    }
}
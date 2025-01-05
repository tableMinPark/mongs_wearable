package com.mongs.wear.data.global.api

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.mongs.wear.data.R
import com.mongs.wear.data.activity.consumer.BattleConsumer
import com.mongs.wear.data.global.consumer.DefaultConsumer
import com.mongs.wear.data.manager.consumer.ManagementConsumer
import com.mongs.wear.data.user.consumer.PlayerConsumer
import dagger.hilt.android.qualifiers.ApplicationContext
import info.mqtt.android.service.MqttAndroidClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@Singleton
class MqttApi @Inject constructor(
    @ApplicationContext private val context: Context,
    private val mqttAndroidClient: MqttAndroidClient,
    private val defaultConsumer: DefaultConsumer,
    private val gson: Gson,
) {

    companion object {
        private const val TAG = "MqttClientApi"
        private var connectPending = false
    }

    fun isConnected(): Boolean = mqttAndroidClient.isConnected

    fun isConnectPending(): Boolean = connectPending

    suspend fun connect() {
        withContext(Dispatchers.IO) {
            connectPending = true

            val options = MqttConnectOptions().apply {
                this.userName = context.getString(R.string.mqtt_username)
                this.password = context.getString(R.string.mqtt_password).toCharArray()
                this.keepAliveInterval = context.getString(R.string.mqtt_keep_alive).toInt()
            }

            mqttAndroidClient.setCallback(defaultConsumer)

            mqttAndroidClient.connect(options).await()

            connectPending = false

            if (mqttAndroidClient.isConnected) {
                Log.i(TAG, "mqtt broker connect success.")
            } else {
                Log.i(TAG, "mqtt broker connect fail.")
            }
        }
    }

    suspend fun disConnect() {
        withContext(Dispatchers.IO) {
            if (mqttAndroidClient.isConnected) {
                mqttAndroidClient.disconnect().await()
                Log.i(TAG, "mqtt broker disConnect.")
            }
        }
    }

    suspend fun subscribe(topic: String) {
        withContext(Dispatchers.IO) {
            if (mqttAndroidClient.isConnected) {
                mqttAndroidClient.subscribe(topic, 2).await()
                Log.i(TAG, "$topic subscribe.")
            } else {
                Log.e(TAG, "$topic subscribe fail.")
            }
        }
    }

    suspend fun disSubscribe(topic: String) {
        withContext(Dispatchers.IO) {
            if (mqttAndroidClient.isConnected) {
                mqttAndroidClient.unsubscribe(topic).await()
                Log.i(TAG, "$topic unSubscribe.")
            } else {
                Log.e(TAG, "$topic unSubscribe fail.")
            }
        }
    }

    suspend fun <T> produce(topic: String, requestDto: T) {
        withContext(Dispatchers.IO) {
            if (mqttAndroidClient.isConnected) {

                val payload = gson.toJson(requestDto).toByteArray()

                mqttAndroidClient.publish(topic = topic, payload = payload, qos = 1, retained = true).await()

                Log.i(TAG, "$topic produce ===> $payload")
            } else {
                Log.e(TAG, "$topic produce fail.")
            }
        }
    }

    private suspend fun IMqttToken.await() = suspendCancellableCoroutine { cont ->
        actionCallback = object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                cont.resume(Unit)
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                cont.resumeWithException(exception ?: Exception("Unknown error"))
            }
        }
    }
}
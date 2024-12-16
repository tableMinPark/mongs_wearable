package com.mongs.wear.data_.activity.api

import android.content.Context
import android.util.Log
import com.mongs.wear.data.R
import com.mongs.wear.data_.activity.consumer.BattleConsumer
import info.mqtt.android.service.MqttAndroidClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class BattleMqttApi (
    private val context: Context,
    private val mqttAndroidClient: MqttAndroidClient,
    private val battleConsumer: BattleConsumer,
) {

    suspend fun connect() {
        withContext(Dispatchers.IO) {
            val options = MqttConnectOptions().apply {
                this.userName = context.getString(R.string.mqtt_username)
                this.password = context.getString(R.string.mqtt_password).toCharArray()
            }
            mqttAndroidClient.setCallback(battleConsumer)
            mqttAndroidClient.connect(options).await()
            Log.i("BattleMqttApi", "connect.")
        }
    }

    suspend fun disConnect() {
        withContext(Dispatchers.IO) {
            if (mqttAndroidClient.isConnected) {
                mqttAndroidClient.disconnect().await()
                Log.i("BattleMqttApi", "disConnect.")
            }
        }
    }

    suspend fun subscribe(topic: String) {
        withContext(Dispatchers.IO) {
            if (mqttAndroidClient.isConnected) {
                mqttAndroidClient.subscribe(topic, 2).await()
                Log.i("BattleMqttApi", "[$topic] subscribe.")
            } else {
                Log.e("BattleMqttApi", "[$topic] subscribe fail.")
            }
        }
    }

    suspend fun disSubscribe(topic: String) {
        withContext(Dispatchers.IO) {
            if (mqttAndroidClient.isConnected) {
                mqttAndroidClient.unsubscribe(topic).await()
                Log.i("BattleMqttApi", "[$topic] unSubscribe.")
            } else {
                Log.e("BattleMqttApi", "[$topic] unSubscribe fail.")
            }
        }
    }

    suspend fun produce(topic: String, payload: String) {
        withContext(Dispatchers.IO) {
            if (mqttAndroidClient.isConnected) {
                mqttAndroidClient.publish(topic = topic, payload = payload.toByteArray(), qos = 1, retained = true).await()
                Log.i("BattleMqttApi", "[$topic] : $payload")
            } else {
                Log.e("BattleMqttApi", "[$topic] produce fail.")
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
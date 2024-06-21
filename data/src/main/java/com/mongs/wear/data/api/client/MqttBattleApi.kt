package com.mongs.wear.data.api.client

import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import com.mongs.wear.data.R
import info.mqtt.android.service.MqttAndroidClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttException
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class MqttBattleApi @Inject constructor(
    private val context: Context,
    private val mqttAndroidClient: MqttAndroidClient
) {

    suspend fun connect(messageCallback: MqttCallback) {
        withContext(Dispatchers.IO) {
            val options = MqttConnectOptions().apply {
                this.userName = context.getString(R.string.mqtt_username)
                this.password = context.getString(R.string.mqtt_password).toCharArray()
            }

            try {
                mqttAndroidClient.setCallback(messageCallback)
                mqttAndroidClient.connect(options).await()
                Log.i("MqttBattleApi", "connect.")
            } catch (e: MqttException) {
                Log.e("MqttBattleApi", "connect fail.")
            }
        }
    }

    suspend fun disConnect() {
        withContext(Dispatchers.IO) {
            if (mqttAndroidClient.isConnected) {
                mqttAndroidClient.disconnect().await()
                Log.i("MqttBattleApi", "disConnect.")
            }
        }
    }

    suspend fun subscribe(topic: String) {
        withContext(Dispatchers.IO) {
            if (mqttAndroidClient.isConnected) {
                mqttAndroidClient.subscribe(topic, 2).await()
                Log.i("MqttBattleApi", "[$topic] subscribe.")
            } else {
                Log.e("MqttBattleApi", "[$topic] subscribe fail.")
            }
        }
    }

    suspend fun disSubscribe(topic: String) {
        withContext(Dispatchers.IO) {
            if (mqttAndroidClient.isConnected) {
                mqttAndroidClient.unsubscribe(topic).await()
                Log.i("MqttBattleApi", "[$topic] unSubscribe.")
            } else {
                Log.e("MqttBattleApi", "[$topic] unSubscribe fail.")
            }
        }
    }

    suspend fun produce(topic: String, payload: String) {
        withContext(Dispatchers.IO) {
            if (mqttAndroidClient.isConnected) {
                mqttAndroidClient.publish(topic = topic, payload = payload.toByteArray(), qos = 1, retained = true).await()
                Log.i("MqttBattleApi", "[$topic] : $payload")
            } else {
                Log.e("MqttBattleApi", "[$topic] produce fail.")
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
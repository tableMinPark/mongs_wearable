package com.mongs.wear.data_.manager.api

import android.content.Context
import android.util.Log
import com.mongs.wear.data.R
import com.mongs.wear.data_.manager.consumer.ManagementConsumer
import info.mqtt.android.service.MqttAndroidClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class ManagementMqttApi @Inject constructor(
    private val context: Context,
    private val mqttAndroidClient: MqttAndroidClient,
    private val managementConsumer: ManagementConsumer,
) {

    suspend fun connect() {
        withContext(Dispatchers.IO) {
            val options = MqttConnectOptions().apply {
                this.userName = context.getString(R.string.mqtt_username)
                this.password = context.getString(R.string.mqtt_password).toCharArray()
            }

            mqttAndroidClient.setCallback(managementConsumer)
            mqttAndroidClient.connect(options = options).await()
            Log.i("ManagementMqttApi", "connect.")
        }
    }

    suspend fun disConnect() {
        withContext(Dispatchers.IO) {
            if (mqttAndroidClient.isConnected) {
                mqttAndroidClient.disconnect().await()
                Log.i("ManagementMqttApi", "disConnect.")
            } else {
                Log.e("ManagementMqttApi", "disConnect fail.")
            }
        }
    }

    suspend fun subscribe(topic: String) {
        withContext(Dispatchers.IO) {
            if (mqttAndroidClient.isConnected) {
                mqttAndroidClient.subscribe(topic, 2).await()
                Log.i("ManagementMqttApi", "[$topic] subscribe.")
            } else {
                Log.e("ManagementMqttApi", "[$topic] subscribe fail.")
            }
        }
    }

    suspend fun disSubscribe(topic: String) {
        withContext(Dispatchers.IO) {
            if (mqttAndroidClient.isConnected) {
                mqttAndroidClient.unsubscribe(topic).await()
                Log.i("ManagementMqttApi", "[$topic] unSubscribe.")
            } else {
                Log.e("ManagementMqttApi", "[$topic] unSubscribe fail.")
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
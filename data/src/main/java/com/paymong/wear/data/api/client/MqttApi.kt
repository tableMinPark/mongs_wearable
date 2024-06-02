package com.paymong.wear.data.api.client

import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import javax.inject.Inject

class MqttApi @Inject constructor(
    private val context: Context,
    private val mqttAndroidClient: MqttAndroidClient
) {
    private var isInit: Boolean = false
    private var isConnected: Boolean = false

    private lateinit var messageCallback: MqttCallback
    private lateinit var connectDisableCallback: () -> Unit
    private lateinit var connectSuccessCallback: () -> Unit

    private val connectCallback = object : IMqttActionListener {
        override fun onSuccess(asyncActionToken: IMqttToken?) {
            CoroutineScope(Dispatchers.IO).launch {
                this@MqttApi.isConnected = true
                this@MqttApi.mqttAndroidClient.setCallback(this@MqttApi.messageCallback)
                this@MqttApi.connectSuccessCallback()
            }
        }
        override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
            CoroutineScope(Dispatchers.IO).launch {
                this@MqttApi.isConnected = false
                this@MqttApi.connectDisableCallback()
            }
        }
    }

    fun init(
        messageCallback: MqttCallback,
        connectDisableCallback: () -> Unit,
        connectSuccessCallback: () -> Unit
    ) {
        this.messageCallback = messageCallback
        this.connectDisableCallback = connectDisableCallback
        this.connectSuccessCallback = connectSuccessCallback
        this.isInit = true
        this.isConnected = false
        this.connect()
    }

    fun subscribe(topic: String, mqttActionListener: IMqttActionListener) {
        if (this.isConnected) {
            this.mqttAndroidClient.subscribe(topic, 2, context, mqttActionListener)
        } else {
            Log.e("MqttApi", "[$topic] subscribe fail.")
        }
    }

    fun disSubscribe(topic: String) {
        if (this.isConnected) {
            this.mqttAndroidClient.unsubscribe(topic)
            Log.i("MqttApi", "[$topic] disSubscribe.")
        } else {
            Log.w("MqttApi", "[$topic] not connected.")
        }
    }

    fun connect() {
        if (this.isInit) {
            this.mqttAndroidClient.connect(MqttConnectOptions(), context, connectCallback)
            Log.i("MqttApi", "connect.")
        } else {
            Log.w("MqttApi", "not init.")
        }
    }

    fun disConnect() {
        if (this.isConnected) {
            this.mqttAndroidClient.disconnect()
            this.isConnected = false
            Log.i("MqttApi", "disconnect.")
        } else {
            Log.w("MqttApi", "not connected.")
        }
    }

    fun reset() {
        this.disConnect()
        this.isInit = false
        this.isConnected = false
        Log.i("MqttApi", "reset.")
    }
}
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
    /** Flag **/
    private var isInit: Boolean = false
    private var isConnected: Boolean = false
    private var isMemberSubscribe: Boolean = false
    private var isMongSubscribe: Boolean = false
    /** Mqtt Object **/
    private lateinit var messageCallback: MqttCallback
    private lateinit var connectDisableCallback: () -> Unit
    private lateinit var connectSuccessCallback: () -> Unit

    /** Connect Callback **/
    private val connectCallback = object : IMqttActionListener {
        override fun onSuccess(asyncActionToken: IMqttToken?) {
            CoroutineScope(Dispatchers.IO).launch {
                Log.d("Mqtt", "[MQTT CONNECT SUCCESS]")
                this@MqttApi.isConnected = true
                this@MqttApi.mqttAndroidClient.setCallback(this@MqttApi.messageCallback)
                this@MqttApi.connectSuccessCallback()
            }
        }
        override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
            CoroutineScope(Dispatchers.IO).launch {
                Log.d("Mqtt", "[MQTT CONNECT FAIL]")
                this@MqttApi.isConnected = false
                this@MqttApi.connectDisableCallback()
            }
        }
    }
    /** Mqtt 설정 초기화 **/
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
    /** Mqtt 구독 **/
    fun subscribe(topic: String, mqttActionListener: IMqttActionListener) {
        if (this.isConnected) {
            this.mqttAndroidClient.subscribe(topic, 2, context, mqttActionListener)
        } else {
            Log.d("Matt", "[SUBSCRIBE $topic FAIL] NOT CONNECT BROKER")
        }
    }
    /** Mqtt 구독 해제 **/
    fun disSubscribe(topic: String) {
        if (this.isConnected) {
            this.mqttAndroidClient.unsubscribe(topic)
            Log.d("Matt", "[DIS_SUBSCRIBE $topic]")
        } else {
            Log.d("Matt", "[DIS_SUBSCRIBE $topic FAIL] NOT CONNECT BROKER")
        }
    }
    /** Mqtt 연결 **/
    fun connect() {
        if (this.isInit) {
            this.mqttAndroidClient.connect(MqttConnectOptions(), context, connectCallback)
        }
    }
    /** Mqtt 연결 해제 **/
    fun disConnect() {
        if (this.isConnected) {
            this.mqttAndroidClient.disconnect()
            this.isConnected = false
        }
    }
    /** Mqtt 설정 재설정 **/
    fun reset() {
        this.disConnect()
        this.isInit = false
        this.isConnected = false
    }
}
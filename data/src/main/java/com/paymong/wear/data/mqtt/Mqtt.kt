package com.paymong.wear.data.mqtt

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import javax.inject.Inject

class Mqtt @Inject constructor(
    private val context: Context
) {
    companion object {
        private const val MAX_CONNECT_FAIL_COUNT = 6 * 10           // 60번 = 10분 (1번 시도 당 10초)
        private const val MAX_SUBSCRIBE_FAIL_COUNT = 6 * 10         // 60번 = 10분 (1번 시도 당 10초)
        private const val CONNECT_DELAY = 10L

        private const val BASE_URL = "tcp://10.0.2.2:1883"
        private val MQTT_CLIENT_ID = MqttClient.generateClientId()
    }

    private val mqttAndroidClient: MqttAndroidClient = MqttAndroidClient(context, BASE_URL, MQTT_CLIENT_ID)
    private lateinit var dataTopic: String
    private lateinit var messageCallback: MqttCallback
    private lateinit var connectDisableCallback: () -> Unit
    private lateinit var connectSuccessCallback: () -> Unit

    private var isInit : Boolean = false
    private var connectFailCount : Int = 0

    fun isInit() : Boolean {
        return isInit
    }

    fun init(
        email: String,
        messageCallback: MqttCallback,
        connectDisableCallback: () -> Unit,
        connectSuccessCallback: () -> Unit
    ) {
        // 변수 초기화
        isInit = true
        connectFailCount = 0
        // 콜백 초기화
        this@Mqtt.messageCallback = messageCallback
        this@Mqtt.connectDisableCallback = connectDisableCallback
        this@Mqtt.connectSuccessCallback = connectSuccessCallback
        // Topic 명 정의
        dataTopic = "data/${email}"
    }

    fun reset() {
        isInit = false
        connectFailCount = 0
        dataTopic = ""
    }

    fun connect() {
        Log.d("Mqtt", "[MQTT CONNECT] [connect]")
        mqttAndroidClient.connect(MqttConnectOptions(), context, connectCallback)
    }
    fun disConnect() {
        Log.d("Mqtt", "[MQTT DISCONNECT] [disConnect]")
        mqttAndroidClient.disconnect()
    }

    private fun subscribe() {
        mqttAndroidClient.subscribe(dataTopic, 2, context, subscribeDataCallback)
    }

    /** Connect Listener **/
    private val connectCallback = object : IMqttActionListener {
        override fun onSuccess(asyncActionToken: IMqttToken?) {
            Log.d("Mqtt", "[MQTT CONNECT] [mqtt connect success]")
            CoroutineScope(Dispatchers.IO).launch {
                connectFailCount = 0
                // 구독 연결
                subscribe()
                // 메시지 도착 시 콜백
                mqttAndroidClient.setCallback(messageCallback)
                connectSuccessCallback()
            }
        }

        override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
            ++connectFailCount
            Log.d("Mqtt", "[MQTT CONNECT] [mqtt connect fail($connectFailCount): ${exception?.message}]")
            CoroutineScope(Dispatchers.IO).launch {
                if (connectFailCount < MAX_CONNECT_FAIL_COUNT) {
                    delay(1000 * CONNECT_DELAY)
                    connect()
                } else {
                    Log.d("Mqtt", "[MQTT CONNECT] [try mqtt connect max count. stop try connect: ${exception?.message}]")
                    // 연결 시도 횟수 초과 시, 연결 불가 플래그 변경
                    connectDisableCallback()
                }
            }
        }
    }


    /** Sub Listener **/
    private val subscribeDataCallback = object : IMqttActionListener {
        override fun onSuccess(asyncActionToken: IMqttToken?) {
            // 구독 성공 시의 처리
            Log.d("Matt", "[SUBSCRIBE] [data subscribe success]")
        }

        override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
            // 구독 실패 시의 처리
            Log.d("Matt", "[SUBSCRIBE] [data subscribe fail: ${exception?.message}]")
            CoroutineScope(Dispatchers.IO).launch {
                if (connectFailCount < MAX_SUBSCRIBE_FAIL_COUNT) {
                    delay(1000 * CONNECT_DELAY)
                    subscribe()
                } else {
                    Log.d("Mqtt", "[SUBSCRIBE] [try mqtt subscribe max count. stop try subscribe: ${exception?.message}]")
                    // 구독 시도 횟수 초과 시, 연결 해제
                    mqttAndroidClient.disconnect()
                }
            }
        }
    }
}
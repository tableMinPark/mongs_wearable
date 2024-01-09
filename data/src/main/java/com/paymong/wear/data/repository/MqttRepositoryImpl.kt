package com.paymong.wear.data.repository

import android.content.Context
import android.util.Log
import com.paymong.wear.data.dataStore.DataSource
import com.paymong.wear.data.room.AppDatabase
import com.paymong.wear.domain.repository.MqttRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage
import javax.inject.Inject

class MqttRepositoryImpl @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val appDatabase: AppDatabase,
    private val dataSource: DataSource
) : MqttRepository {
    companion object {
        private const val MAX_CONNECT_FAIL_COUNT = 2
        private const val CONNECT_DELAY = 10L

        private const val BASE_URL = "tcp://10.0.2.2:1883"
        private val MQTT_CLIENT_ID = MqttClient.generateClientId()
    }
    private val mqttAndroidClient: MqttAndroidClient = MqttAndroidClient(context, BASE_URL, MQTT_CLIENT_ID)
    private lateinit var appInfoTopic : String
    private lateinit var slotTopic : String

    private var connectFailCount : Int = 0

    override fun initMqtt(email: String) {
        // 변수 초기화
        connectFailCount = 0
        // Topic 명 정의
        appInfoTopic = "appInfo/${email}"
        slotTopic = "slot/${email}"
        // MQTT 연결
        connect()
    }

    private fun connect() {
        val mqttConnectOptions = MqttConnectOptions()
        mqttAndroidClient.connect(mqttConnectOptions, context, connectCallback)
    }
    private fun subscribeAppInfo() {
        mqttAndroidClient.subscribe(appInfoTopic, 0, context, subscribeAppInfoCallback)
    }
    private fun subscribeSlot() {
        mqttAndroidClient.subscribe(appInfoTopic, 0, context, subscribeSlotCallback)
    }

    /** Connect Listener **/
    private val connectCallback = object : IMqttActionListener {
        override fun onSuccess(asyncActionToken: IMqttToken?) {
            Log.d("MqttRepositoryImpl", "mqtt connect success")
            CoroutineScope(Dispatchers.IO).launch {
                connectFailCount = 0
                // 구독 연결
                subscribeAppInfo()
                subscribeSlot()
                // 메시지 도착 시 콜백
                mqttAndroidClient.setCallback(messageCallback)
                dataSource.setNetworkFlag(flag = true)
            }
        }

        override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
            ++connectFailCount
            Log.d("MqttRepositoryImpl", "mqtt connect fail($connectFailCount): ${exception?.message}")
            CoroutineScope(Dispatchers.IO).launch {
                if (connectFailCount < MAX_CONNECT_FAIL_COUNT) {
                    delay(10000)
                    connect()
                } else {
                    Log.d("MqttRepositoryImpl", "try mqtt connect max count. stop try connect: ${exception?.message}")
                    // 연결 시도 횟수 초과 시, 연결 불가 플래그 변경
                    dataSource.setNetworkFlag(flag = false)
                }
            }
        }
    }

    /** Sub Listener **/
    private val subscribeAppInfoCallback = object : IMqttActionListener {
        override fun onSuccess(asyncActionToken: IMqttToken?) {
            // 구독 성공 시의 처리
            Log.d("MqttRepositoryImpl", "appInfoTopic subscribe success")
        }

        override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
            // 구독 실패 시의 처리
            Log.d("MqttRepositoryImpl", "appInfoTopic  subscribe fail: ${exception?.message}")
            CoroutineScope(Dispatchers.IO).launch {
                delay(1000 * CONNECT_DELAY)
                subscribeAppInfo()
            }
        }
    }
    private val subscribeSlotCallback = object : IMqttActionListener {
        override fun onSuccess(asyncActionToken: IMqttToken?) {
            // 구독 성공 시의 처리
            Log.d("MqttRepositoryImpl", "slotTopic subscribe success")
        }

        override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
            // 구독 실패 시의 처리
            Log.d("MqttRepositoryImpl", "slotTopic subscribe fail: ${exception?.message}")
            CoroutineScope(Dispatchers.IO).launch {
                delay(1000 * CONNECT_DELAY)
                subscribeSlot()
            }
        }
    }

    /** Message Arrived Listener **/
    private val messageCallback = object : MqttCallback {
        override fun connectionLost(cause: Throwable?) {
            // 연결이 끊겼을 때의 처리
            Log.d("MqttRepositoryImpl", "mqtt connecting disable: ${cause?.message}")
            connect()
        }

        override fun messageArrived(topic: String?, message: MqttMessage?) {
            // 메시지 도착 시의 처리 (수신)
            message?.let {
                Log.d("MqttRepositoryImpl", "mqtt message arrived: ${message.payload}")
                when(topic) {
                    "appInfo" -> {
                        Log.d("MqttRepositoryImpl", "appInfo : ${it.payload}")
                    }
                    "slot" -> {
                        Log.d("MqttRepositoryImpl", "slot : ${it.payload}")
                    }
                    else -> {
                        Log.d("MqttRepositoryImpl", "else : ${it.payload}")
                    }
                }
            }
        }

        override fun deliveryComplete(token: IMqttDeliveryToken?) {
            // 메시지 발행 완료 시의 처리 (전송)
        }
    }
}
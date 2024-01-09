package com.paymong.wear.data.repository

import android.content.Context
import android.util.Log
import com.paymong.wear.data.dataStore.DataSource
import com.paymong.wear.data.room.AppDatabase
import com.paymong.wear.domain.repository.MqttRepository
import dagger.hilt.android.qualifiers.ApplicationContext
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
    @ApplicationContext context: Context,
    private val appDatabase: AppDatabase,
    private val dataSource: DataSource
) : MqttRepository {
    companion object {
        private const val BASE_URL = "tcp://10.0.2.2:1883"
        private val MQTT_CLIENT_ID = MqttClient.generateClientId()
    }
    private val mqttAndroidClient: MqttAndroidClient = MqttAndroidClient(context, BASE_URL, MQTT_CLIENT_ID)
    private lateinit var email : String

    override fun initMqtt(email: String) {
        this@MqttRepositoryImpl.email = email
        // MQTT 연결
        val mqttConnectOptions = MqttConnectOptions()
        mqttAndroidClient.connect(mqttConnectOptions, null, connectCallback)
        // 메시지 도착 시 콜백
        mqttAndroidClient.setCallback(messageCallback)
    }

    /** Sub Listener **/
    private val connectCallback = object : IMqttActionListener {
        override fun onSuccess(asyncActionToken: IMqttToken?) {
            // 연결 성공 시의 처리
            Log.d("MqttRepositoryImpl", "mqtt connect success")
            // 구독 연결
            mqttAndroidClient.subscribe("appInfo/${email}", 0, context, subscribeCallback)
            mqttAndroidClient.subscribe("slot/${email}", 0, context, subscribeCallback)
        }

        override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
            Log.d("MqttRepositoryImpl", "mqtt connect fail: ${exception?.message}")
        }
    }
    private val subscribeCallback = object : IMqttActionListener {
        override fun onSuccess(asyncActionToken: IMqttToken?) {
            // 구독 성공 시의 처리
            Log.d("MqttRepositoryImpl", "${asyncActionToken?.messageId} subscribe success")
        }

        override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
            // 구독 실패 시의 처리
            Log.d("MqttRepositoryImpl", "${asyncActionToken?.messageId} subscribe fail: ${exception?.message}")
        }
    }
    private val messageCallback = object : MqttCallback {
        override fun connectionLost(cause: Throwable?) {
            // 연결이 끊겼을 때의 처리
            Log.d("MqttRepositoryImpl", "mqtt connecting disable: ${cause?.message}")
        }

        override fun messageArrived(topic: String?, message: MqttMessage?) {
            // 메시지 도착 시의 처리 (수신)
            message?.let {
                Log.d("MqttRepositoryImpl", "mqtt message arrived: ${message?.payload}")
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
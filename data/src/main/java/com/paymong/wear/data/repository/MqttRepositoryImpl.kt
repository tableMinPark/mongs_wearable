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
    }
    private val BASE_URL = "tcp://broker.emqx.io:1883"
    private val MQTT_CLIENT_ID = MqttClient.generateClientId()
    private val mqttAndroidClient: MqttAndroidClient = MqttAndroidClient(context, BASE_URL, MQTT_CLIENT_ID)

    override fun initMqtt(email: String) {
        // MQTT 연결
        val mqttConnectOptions = MqttConnectOptions()
        mqttAndroidClient.connect(mqttConnectOptions, null, connectCallback)
        // 구독 연결
        mqttAndroidClient.subscribe("topic/appInfo/${email}", 0, null, subscribeCallback)
        mqttAndroidClient.subscribe("topic/slot/${email}", 0, null, subscribeCallback)
        // 메시지 도착 시 콜백
        mqttAndroidClient.setCallback(messageCallback)
    }

    /** Sub Listener **/
    private val connectCallback = object : IMqttActionListener {
        override fun onSuccess(asyncActionToken: IMqttToken?) {
            // 연결 성공 시의 처리
        }

        override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
            // 연결 실패 시의 처리
        }
    }
    private val subscribeCallback = object : IMqttActionListener {
        override fun onSuccess(asyncActionToken: IMqttToken?) {
            // 구독 성공 시의 처리
        }

        override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
            // 구독 실패 시의 처리
        }
    }
    private val messageCallback = object : MqttCallback {
        override fun connectionLost(cause: Throwable?) {
            // 연결이 끊겼을 때의 처리
        }

        override fun messageArrived(topic: String?, message: MqttMessage?) {
            // 메시지 도착 시의 처리 (수신)
            message?.let {
                when(topic) {
                    "appInfo" -> {
                        Log.d("MqttRepository", "appInfo : $it")
                    }
                    "slot" -> {
                        Log.d("MqttRepository", "slot : $it")
                    }
                    else -> {
                        Log.d("MqttRepository", "else : $it")
                    }
                }
            }
        }

        override fun deliveryComplete(token: IMqttDeliveryToken?) {
            // 메시지 발행 완료 시의 처리 (전송)
        }
    }
}
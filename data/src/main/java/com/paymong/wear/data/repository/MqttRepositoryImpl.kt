package com.paymong.wear.data.repository

import android.util.Log
import com.paymong.wear.data.dataStore.DataSource
import com.paymong.wear.data.mqtt.Mqtt
import com.paymong.wear.data.room.AppDatabase
import com.paymong.wear.domain.repository.MqttRepository
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttMessage
import javax.inject.Inject

class MqttRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase,
    private val dataSource: DataSource,
    private val mqtt: Mqtt
) : MqttRepository {
    private var isReconnect : Boolean = false

    override fun initDataReset() {
        mqtt.reset()
    }
    override fun connectBeforeInit(email: String) {
        isReconnect = true
        dataSource.setNetworkFlag(false)

        mqtt.init(
            email = email,
            messageCallback = messageCallback,
            connectDisableCallback = { dataSource.setNetworkFlag(false) },
            connectSuccessCallback = {
                dataSource.setNetworkFlag(true)
            }
        )
        // 연결 시도
        mqtt.connect()
    }
    override fun connectAfterInit() {
        if (!mqtt.isInit()) {
            return
        }

        // TODO : 재 연결이기 때문에 API 호출하여 데이터 동기화 필요함

        isReconnect = true
        mqtt.connect()
    }
    override fun disConnectNotReset() {
        isReconnect = false
        // 연결 해제
        mqtt.disConnect()
    }
    override fun disConnectAndReset() {
        isReconnect = false
        // 연결 해제
        mqtt.disConnect()
        // 객체 변수 초기화
        mqtt.reset()
    }

    /** Message Arrived Listener **/
    private val messageCallback = object : MqttCallback {
        override fun connectionLost(cause: Throwable?) {
            // 연결이 끊겼을 때의 처리
            Log.d("MqttRepositoryImpl", "[MQTT CONNECT DISABLE] [mqtt connecting disable: ${cause?.message}]")
            if (!isReconnect) {
                return
            }
            mqtt.connect()
        }

        override fun messageArrived(topic: String?, message: MqttMessage?) {
            // 메시지 도착 시의 처리 (수신)
            message?.let {
                Log.d("MqttRepositoryImpl", "data : $it")
                // TODO : 도착한 문자열 데이터를 파싱 후, ROOM 에 데이터 동기화
            }
        }

        override fun deliveryComplete(token: IMqttDeliveryToken?) {
            // 메시지 발행 완료 시의 처리 (전송)
        }
    }
}
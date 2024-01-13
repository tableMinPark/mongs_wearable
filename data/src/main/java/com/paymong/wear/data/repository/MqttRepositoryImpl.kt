package com.paymong.wear.data.repository

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.paymong.wear.data.dataStore.DataSource
import com.paymong.wear.data.mqtt.Mqtt
import com.paymong.wear.data.mqtt.code.MqttCode
import com.paymong.wear.data.mqtt.model.response.MapResModel
import com.paymong.wear.data.mqtt.model.response.StateResModel
import com.paymong.wear.data.mqtt.model.response.StatusResModel
import com.paymong.wear.data.room.AppDatabase
import com.paymong.wear.data.util.GsonDateFormatAdapter
import com.paymong.wear.domain.repository.MqttRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttMessage
import java.time.LocalDateTime
import javax.inject.Inject

class MqttRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase,
    private val dataSource: DataSource,
    private val mqtt: Mqtt
) : MqttRepository {
    private var isReconnect : Boolean = false
    private var gson: Gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, GsonDateFormatAdapter())
        .create()

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

    private fun updateMap(data: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val mapResModel = gson.fromJson(data, MapResModel::class.java)
            dataSource.setMapCode(mapCode = mapResModel.mapCode)
        }
    }
    private fun updateStatus(data: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val statusResModel = gson.fromJson(data, StatusResModel::class.java)
            appDatabase.slotBySlotIdDao().modifyStatusByMongId(
                mongId = statusResModel.mongId,
                health = statusResModel.health,
                satiety = statusResModel.satiety,
                strength = statusResModel.strength,
                sleep = statusResModel.sleep
            )
        }
    }
    private fun updateState(data: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val stateResModel = gson.fromJson(data, StateResModel::class.java)
            appDatabase.slotBySlotIdDao().modifyStateByMongId(
                mongId = stateResModel.mongId,
                stateCode = stateResModel.stateCode
            )
        }
    }
    private fun updateMong(data: String) {
        CoroutineScope(Dispatchers.IO).launch {
//            val mongResModel = gson.fromJson(data, )
        }
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
                val json = it.toString()
                Log.d("MqttRepositoryImpl", "json : $json")

                try {
                    val resModel = gson.fromJson(json, Map::class.java)
                    val code = MqttCode.valueOf(resModel["code"].toString())
                    val data = resModel["data"].toString()
                    Log.d("MqttRepositoryImpl", "code : $code, data : $data")

                    when (code) {
                        /** 맵 **/
                        MqttCode.MAP -> {
                            updateMap(data)
                        }
                        /** 지수 **/
                        MqttCode.STATUS -> {
                            updateStatus(data)
                        }
                        /** 상태 **/
                        MqttCode.STATE -> {
                            updateState(data)
                        }
                        /** 몽 **/
                        MqttCode.MONG -> {
                            updateMong(data)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        override fun deliveryComplete(token: IMqttDeliveryToken?) {
            // 메시지 발행 완료 시의 처리 (전송)
        }
    }
}
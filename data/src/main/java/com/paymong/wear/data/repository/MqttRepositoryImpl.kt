package com.paymong.wear.data.repository

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.paymong.wear.data.dataStore.DataSource
import com.paymong.wear.data.mqtt.Mqtt
import com.paymong.wear.data.mqtt.code.MqttCode
import com.paymong.wear.data.mqtt.model.response.MapResModel
import com.paymong.wear.data.mqtt.model.response.EvolutionResModel
import com.paymong.wear.data.mqtt.model.response.GraduationResModel
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
import java.util.PriorityQueue
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

    private var isUpdateData = false
    private val arrivedMessage = PriorityQueue<String>(PriorityQueue())

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
//                liveUpdateData()
            },
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

    private fun updateDataLive() {
        CoroutineScope(Dispatchers.IO).launch {
            isUpdateData = true
            Log.d("MqttRepositoryImpl", "[UPDATE DATA] start! job: ${arrivedMessage.size}")
            while(!arrivedMessage.isEmpty()) {
                val json = arrivedMessage.poll()
                updateData(json = json!!)
            }
            Log.d("MqttRepositoryImpl", "[UPDATE DATA] stop!")
            isUpdateData = false
        }
    }
    private suspend fun updateData(json: String) {
        try {
            val resModel = gson.fromJson(json, Map::class.java)
            val data = resModel["data"].toString()

            // code 값에 따라 적합한 Model 에 매핑
            when (MqttCode.valueOf(resModel["code"].toString())) {
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
                    val mongId = resModel["mongId"].toString().toLong()
                    val stateCode = appDatabase.slotDao().findStateByMongId(mongId = mongId)
                    // 정상, 아픔, 졸림, 배고픔, 죽음
                    // 변경 사항 보류 조건 : 수면, 진화 대기, 먹는중, 행복, 진화 중
                    // TODO : 변경 사항 재개 조건 : 정상, 아픔, 졸림, 배고픔
                    // 변경 사항 삭제 조건 : 죽음, 졸업
                    if (stateCode in arrayListOf("CD002", "CD007", "CD008", "CD009", "CD010")) {
                        arrivedMessage.add(json)
                    } else if (stateCode !in arrayListOf("CD005", "CD006")) {
                        updateState(data)
                    }
                }
                /** 진화 **/
                MqttCode.EVOLUTION -> {
                    // 진화 대기
                    updateEvolution(data)
//                    val mongId = resModel["mongId"].toString().toLong()
//                    val stateCode = appDatabase.slotDao().findStateByMongId(mongId = mongId)
//                    // 변경 사항 보류 조건 : 먹는중, 행복, 진화 대기, 진화 중
//                    // TODO : 변경 사항 재개 조건 : 정상, 아픔, 졸림, 배고픔
//                    // 변경 사항 삭제 조건 : 죽음, 졸업
//                    if (stateCode in arrayListOf("CD007", "CD008", "CD009", "CD010")) {
//                        arrivedMessage.add(json)
//                    } else if (stateCode !in arrayListOf("CD005", "CD006")) {
//                        updateEvolution(data, stateCode)
//                    }
                }
                /** 졸업 **/
                MqttCode.GRADUATION -> {
                    // 졸업
                    val mongId = resModel["mongId"].toString().toLong()
                    val stateCode = appDatabase.slotDao().findStateByMongId(mongId = mongId)
                    // 변경 사항 보류 조건 : 먹는중, 행복, 진화 대기, 진화 중
                    // TODO : 변경 사항 재개 조건 : 정상, 아픔, 졸림, 배고픔
                    // 변경 사항 삭제 조건 : 죽음, 졸업
                    if (stateCode in arrayListOf("CD007", "CD008", "CD009", "CD010")) {
                        arrivedMessage.add(json)
                    } else if (stateCode !in arrayListOf("CD005", "CD006")) {
                        updateGraduation(data)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private suspend fun updateMap(data: String) {
        Log.d("MqttRepositoryImpl", "[UPDATE MAP] data : $data")
        val mapResModel = gson.fromJson(data, MapResModel::class.java)
        dataSource.setMapCode(mapCode = mapResModel.mapCode)
    }
    private fun updateStatus(data: String) {
        Log.d("MqttRepositoryImpl", "[UPDATE STATUS] data : $data")
        val statusResModel = gson.fromJson(data, StatusResModel::class.java)

        appDatabase.slotDao().modifyStatusByMongId(
            mongId = statusResModel.mongId,
            health = statusResModel.health,
            satiety = statusResModel.satiety,
            strength = statusResModel.strength,
            sleep = statusResModel.sleep
        )
    }
    private fun updateState(data: String) {
        Log.d("MqttRepositoryImpl", "[UPDATE STATE] data : $data")
        val stateResModel = gson.fromJson(data, StateResModel::class.java)
        appDatabase.slotDao().modifyStateByMongId(
            mongId = stateResModel.mongId,
            stateCode = stateResModel.stateCode
        )
    }
    private fun updateEvolution(data: String) {
        Log.d("MqttRepositoryImpl", "[UPDATE EVOLUTION] data : $data")
        val evolutionResModel = gson.fromJson(data, EvolutionResModel::class.java)
        appDatabase.slotDao().modifyShiftByMongId(
            mongId = evolutionResModel.mongId,
            shiftCode = evolutionResModel.shiftCode
        )
        appDatabase.slotDao().modifyNextMongCodeByMongId(
            mongId = evolutionResModel.mongId,
            nextMongCode = evolutionResModel.nextMongCode
        )
    }
    private fun updateGraduation(data: String) {
        Log.d("MqttRepositoryImpl", "[UPDATE GRADUATION] data : $data")
        val graduationResModel = gson.fromJson(data, GraduationResModel::class.java)
        appDatabase.slotDao().modifyShiftByMongId(
            mongId = graduationResModel.mongId,
            shiftCode = graduationResModel.shiftCode
        )
    }

    /** Message Arrived Listener **/
    private val messageCallback = object : MqttCallback {
        override fun connectionLost(cause: Throwable?) {
            // 연결이 끊겼을 때의 처리
            Log.d("MqttRepositoryImpl", "[MQTT CONNECT DISABLE] [mqtt connecting disable: ${cause?.message}]")
            if (isReconnect) {
                mqtt.connect()
            }
        }

        override fun messageArrived(topic: String?, message: MqttMessage?) {
            // 메시지 도착 시의 처리 (수신)
            message?.let {
                val json = it.toString()
                Log.d("MqttRepositoryImpl", "json : $json")
                arrivedMessage.add(json)
                // 업데이트가 진행중이지 않으면 진행
                if (!isUpdateData) {
                    updateDataLive()
                }
            }
        }

        override fun deliveryComplete(token: IMqttDeliveryToken?) {
            // 메시지 발행 완료 시의 처리 (전송)
        }
    }
}
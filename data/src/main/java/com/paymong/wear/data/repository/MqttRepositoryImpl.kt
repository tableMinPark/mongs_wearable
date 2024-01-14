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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttMessage
import java.time.LocalDateTime
import java.util.LinkedList
import java.util.PriorityQueue
import java.util.Queue
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

    private var isUpdate : Boolean = false
    private val arrivedMessage : Queue<String> = LinkedList()

    private fun liveUpdateData() {
        CoroutineScope(Dispatchers.IO).launch {
            while(isUpdate) {
                Log.d("MqttRepositoryImpl", "[MQTT UPDATE DATA] job: ${arrivedMessage.size}")
                while(!arrivedMessage.isEmpty()) {
                    updateData(json = arrivedMessage.poll()!!)
                    delay(5000)
                }
                delay(5000)
            }
            Log.d("MqttRepositoryImpl", "[MQTT UPDATE DATA] stop")
        }
    }

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
                isUpdate = true
                liveUpdateData()
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

    private fun updateMap(json: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val resModel = gson.fromJson(json, Map::class.java)
            val data = resModel["data"].toString()

            Log.d("MqttRepositoryImpl", "[UPDATE MAP] data : $data")
            val mapResModel = gson.fromJson(data, MapResModel::class.java)
            dataSource.setMapCode(mapCode = mapResModel.mapCode)
        }
    }
    private fun updateStatus(json: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val resModel = gson.fromJson(json, Map::class.java)
            val data = resModel["data"].toString()

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
    }
    private fun updateState(json: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val resModel = gson.fromJson(json, Map::class.java)
            val data = resModel["data"].toString()

            Log.d("MqttRepositoryImpl", "[UPDATE STATE] data : $data")
            val stateResModel = gson.fromJson(data, StateResModel::class.java)

            val stateCode = appDatabase.slotDao().findStateByMongId(mongId = stateResModel.mongId)

            // 진화 대기, 진화 중 상태인 경우 nextStateCode 를 변경함
            if (stateCode in arrayListOf("CH007", "CH008", "CH009", "CH010")) {
                appDatabase.slotDao().modifyNextStateByMongId(
                    mongId = stateResModel.mongId,
                    nextStateCode = stateResModel.stateCode
                )
            } else {
                appDatabase.slotDao().modifyStateByMongId(
                    mongId = stateResModel.mongId,
                    stateCode = stateResModel.stateCode
                )
            }
        }
    }
    private fun updateEvolution(json: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val resModel = gson.fromJson(json, Map::class.java)
            val data = resModel["data"].toString()

            Log.d("MqttRepositoryImpl", "[UPDATE EVOLUTION] data : $data")
            val evolutionResModel = gson.fromJson(data, EvolutionResModel::class.java)

            val stateCode = appDatabase.slotDao().findStateByMongId(evolutionResModel.mongId)
            // 현재 상태 이후 진행 (수면, 먹는 중, 행복, 진화 중)
            if (stateCode in arrayListOf("CD002", "CD008", "CD009", "CD010")) {
                Log.d("MqttRepositoryImpl", "[WAIT JOB] stateCode: $stateCode, data: $data")
                arrivedMessage.add(json)
                return@launch
            }
            // 현재 상태가 아래와 같을 경우 수정 하지 않음 (죽음, 졸업, 진화 대기)
            else if (stateCode in arrayListOf("CD005", "CD006", "CD007")) {
                return@launch
            }

            appDatabase.slotDao().modifyNextStateByMongId(
                nextStateCode = stateCode,
                mongId = evolutionResModel.mongId
            )
            appDatabase.slotDao().modifyStateByMongId(
                mongId = evolutionResModel.mongId,
                stateCode = evolutionResModel.stateCode
            )
            appDatabase.slotDao().modifyNextMongCodeByMongId(
                mongId = evolutionResModel.mongId,
                nextMongCode = evolutionResModel.nextMongCode
            )
        }
    }
    private fun updateGraduation(json: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val resModel = gson.fromJson(json, Map::class.java)
            val data = resModel["data"].toString()

            Log.d("MqttRepositoryImpl", "[UPDATE GRADUATION] data : $data")
            val graduationResModel = gson.fromJson(data, GraduationResModel::class.java)

            val stateCode = appDatabase.slotDao().findStateByMongId(graduationResModel.mongId)
            // 현재 상태 이후 진행 (수면, 진화 대기, 먹는 중, 행복, 진화 중)
            if (stateCode in arrayListOf("CD002", "CD007", "CD008", "CD009", "CD010")) {
                arrivedMessage.add(json)
                return@launch
            }
            // 현재 상태가 아래와 같을 경우 수정 하지 않음 (죽음, 졸업)
            else if (stateCode in arrayListOf("CD005", "CD006",)) {
                return@launch
            }

            appDatabase.slotDao().modifyStateByMongId(
                mongId = graduationResModel.mongId,
                stateCode = graduationResModel.stateCode
            )
        }
    }

    private fun updateData(json: String) {
        try {
            val resModel = gson.fromJson(json, Map::class.java)

            // code 값에 따라 적합한 Model 에 매핑
            return when (MqttCode.valueOf(resModel["code"].toString())) {
                /** 맵 **/
                MqttCode.MAP -> {
                    updateMap(json)
                }
                /** 지수 **/
                MqttCode.STATUS -> {
                    updateStatus(json)
                }
                /** 상태 **/
                MqttCode.STATE -> {
                    updateState(json)
                }
                /** 진화 **/
                MqttCode.EVOLUTION -> {
                    updateEvolution(json)
                }
                /** 졸업 **/
                MqttCode.GRADUATION -> {
                    updateGraduation(json)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /** Message Arrived Listener **/
    private val messageCallback = object : MqttCallback {
        override fun connectionLost(cause: Throwable?) {
            // 연결이 끊겼을 때의 처리
            Log.d("MqttRepositoryImpl", "[MQTT CONNECT DISABLE] [mqtt connecting disable: ${cause?.message}]")
            // 연결이 끊겼을 때, 업데이트 반복문 중지
            isUpdate = false
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
            }
        }

        override fun deliveryComplete(token: IMqttDeliveryToken?) {
            // 메시지 발행 완료 시의 처리 (전송)
        }
    }
}
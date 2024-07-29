package com.mongs.wear.data.client

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.mongs.wear.data.R
import com.mongs.wear.data.api.client.MqttEventApi
import com.mongs.wear.data.api.code.MqttCode
import com.mongs.wear.data.callback.MessageEventCallback
import com.mongs.wear.data.code.SlotShift
import com.mongs.wear.data.code.SlotState
import com.mongs.wear.data.dataStore.DeviceDataStore
import com.mongs.wear.data.dataStore.MemberDataStore
import com.mongs.wear.data.room.client.RoomDB
import com.mongs.wear.domain.client.MqttEventClient
import com.mongs.wear.domain.error.ClientErrorCode
import com.mongs.wear.domain.exception.ClientException
import kotlinx.coroutines.delay
import org.eclipse.paho.client.mqttv3.MqttException
import javax.inject.Inject

class MqttEventClientImpl @Inject constructor(
    private val gson: Gson,
    private val context: Context,
    private val memberDataStore: MemberDataStore,
    private val deviceDataStore: DeviceDataStore,
    private val mqttEventApi: MqttEventApi,
    private val roomDB: RoomDB,
): MqttEventClient {
    private val eventMemberBaseTopic = context.getString(R.string.mqtt_event_member_base_topic)
    private val eventMongBaseTopic = context.getString(R.string.mqtt_event_mong_base_topic)

    private var mqttFlag = MqttCode.STOP
    private var eventMemberTopic = ""
    private var eventMongTopic = ""

    private val messageCallback = MessageEventCallback(
        gson = gson,
        lostConnectCallback = {
            if (mqttFlag == MqttCode.CONNECT) {
                // 갑자기 다운
//                eventMemberTopic = ""
//                eventMongTopic = ""
                deviceDataStore.setNetworkFlag(networkFlag = false)
//                mqttFlag = MqttCode.STOP

//                var isReconnectSuccess = false
//
//                while (!isReconnectSuccess) {
//                    try {
//                        this.setConnection()
//                        this.disconnect()
//                        isReconnectSuccess = true
//                    } catch (e: ClientException) {
//                        Log.e("MqttEventClientImpl", "connect fail after lost connection.")
//                        delay(5000)
//                    }
//                }
//
//                delay(5000)
//                deviceDataStore.setNetworkFlag(networkFlag = true)
            }
        },
        setStarPoint = { starPoint ->
            memberDataStore.setStarPoint(starPoint = starPoint)
        },
        updateMongCode = { mongId, mongCode ->
            roomDB.slotDao()
                .updateMongCodeByMqtt(mongId = mongId, mongCode = mongCode)
        },
        updateExp = { mongId, expPercent ->
            roomDB.slotDao().updateExpByMqtt(mongId = mongId, exp = expPercent)
        },
        updateIsSleeping = { mongId, isSleeping ->
            roomDB.slotDao()
                .updateIsSleepingByMqtt(mongId = mongId, isSleeping = isSleeping)
        },
        updatePayPoint = { mongId, payPoint ->
            roomDB.slotDao()
                .updatePayPointByMqtt(mongId = mongId, payPoint = payPoint)
        },
        updatePoopCount = { mongId, poopCount ->
            roomDB.slotDao()
                .updatePoopCountByMqtt(mongId = mongId, poopCount = poopCount)
        },
        updateShiftCode = { mongId, shiftCode ->
            roomDB.slotDao().updateShiftCodeByMqtt(
                mongId = mongId,
                shiftCode = SlotShift.valueOf(shiftCode).code
            )
        },
        updateStateCode = { mongId, stateCode ->
            roomDB.slotDao().updateStateCodeByMqtt(
                mongId = mongId,
                stateCode = SlotState.valueOf(stateCode).code
            )
        },
        updateStatus = { mongId, weight, strengthPercent, satietyPercent, healthyPercent, sleepPercent ->
            roomDB.slotDao().updateStatusByMqtt(
                mongId = mongId,
                weight = weight,
                strength = strengthPercent,
                satiety = satietyPercent,
                healthy = healthyPercent,
                sleep = sleepPercent
            )
        },
    )

    override suspend fun setConnection() {
        try {
            if (mqttFlag == MqttCode.STOP) {
                mqttEventApi.connect(messageCallback = messageCallback)

                deviceDataStore.setNetworkFlag(networkFlag = true)
                mqttFlag = MqttCode.CONNECT
            }
        } catch (e: MqttException) {
            throw ClientException(
                errorCode = ClientErrorCode.MQTT_EVENT_CONNECT_FAIL,
                throwable = e,
            )
        }
    }
    override suspend fun reconnect(resetMember: () -> Unit, resetSlot: () -> Unit) {
        try {
            if (mqttFlag == MqttCode.PAUSE_STOP) {
                mqttEventApi.connect(messageCallback = messageCallback)

                deviceDataStore.setNetworkFlag(networkFlag = true)
                mqttFlag = MqttCode.CONNECT

                if (eventMemberTopic.isNotBlank()) {
                    resetMember()
                    mqttEventApi.subscribe(topic = eventMemberTopic)
                }
                if (eventMongTopic.isNotBlank()) {
                    resetSlot()
                    mqttEventApi.subscribe(topic = eventMongTopic)
                }
            }
        } catch (e: MqttException) {
            e.printStackTrace()
            throw ClientException(
                errorCode = ClientErrorCode.MQTT_EVENT_CONNECT_FAIL,
                throwable = e,
            )
        }
    }

    override suspend fun pauseConnect() {
        try {
            if (mqttFlag == MqttCode.CONNECT) {
                mqttFlag = MqttCode.PAUSE_STOP

                if (eventMemberTopic.isNotBlank()) {
                    mqttEventApi.disSubscribe(topic = eventMemberTopic)
                }
                if (eventMongTopic.isNotBlank()) {
                    mqttEventApi.disSubscribe(topic = eventMongTopic)
                }
                mqttEventApi.disConnect()
            }
        } catch (e: MqttException) {
            throw ClientException(
                errorCode = ClientErrorCode.MQTT_EVENT_PAUSE_CONNECT_FAIL,
                throwable = e,
            )
        }
    }
    override suspend fun disconnect() {
        try {
            if (mqttFlag == MqttCode.CONNECT) {
                eventMemberTopic = ""
                eventMongTopic = ""
                mqttFlag = MqttCode.STOP

                if (eventMemberTopic.isNotBlank()) {
                    mqttEventApi.disSubscribe(topic = eventMemberTopic)
                }
                if (eventMongTopic.isNotBlank()) {
                    mqttEventApi.disSubscribe(topic = eventMongTopic)
                }
                mqttEventApi.disConnect()
            }
        } catch (e: MqttException) {
            throw ClientException(
                errorCode = ClientErrorCode.MQTT_EVENT_DISCONNECT_FAIL,
                throwable = e,
            )
        }
    }
    override suspend fun subScribeMember(accountId: Long) {
        try {
            val newMemberTopic = "$eventMemberBaseTopic/$accountId"
            if (eventMemberTopic != newMemberTopic) {
                this.disSubScribeMember()
                eventMemberTopic = newMemberTopic
                mqttEventApi.subscribe(topic = eventMemberTopic)
            }
        } catch (e: MqttException) {
            throw ClientException(
                errorCode = ClientErrorCode.MQTT_EVENT_SUBSCRIBE_FAIL,
                throwable = e,
            )
        }
    }
    override suspend fun disSubScribeMember() {
        try {
            if (eventMemberTopic.isNotBlank()) {
                mqttEventApi.disSubscribe(topic = eventMemberTopic)
                eventMemberTopic = ""
            }
        } catch (e: MqttException) {
            throw ClientException(
                errorCode = ClientErrorCode.MQTT_EVENT_UNSUBSCRIBE_FAIL,
                throwable = e,
            )
        }
    }
    override suspend fun subScribeMong(mongId: Long) {
        try {
            val newMongTopic ="$eventMongBaseTopic/$mongId"
            if (eventMongTopic != newMongTopic) {
                this.disSubScribeMong()
                eventMongTopic = newMongTopic
                mqttEventApi.subscribe(topic = eventMongTopic)
            }
        } catch (e: MqttException) {
            throw ClientException(
                errorCode = ClientErrorCode.MQTT_EVENT_SUBSCRIBE_FAIL,
                throwable = e,
            )
        }
    }
    override suspend fun disSubScribeMong() {
        try {
            if (eventMongTopic.isNotBlank()) {
                mqttEventApi.disSubscribe(topic = eventMongTopic)
                eventMongTopic = ""
            }
        } catch (e: MqttException) {
            throw ClientException(
                errorCode = ClientErrorCode.MQTT_EVENT_UNSUBSCRIBE_FAIL,
                throwable = e,
            )
        }
    }
}
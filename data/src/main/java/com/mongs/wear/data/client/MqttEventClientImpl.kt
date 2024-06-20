package com.mongs.wear.data.client

import com.mongs.wear.data.api.client.MqttEventApi
import com.mongs.wear.data.callback.MessageEventCallback
import com.mongs.wear.data.code.Shift
import com.mongs.wear.data.code.State
import com.mongs.wear.data.dataStore.MemberDataStore
import com.mongs.wear.data.room.client.RoomDB
import com.mongs.wear.domain.client.MqttEventClient
import javax.inject.Inject

class MqttEventClientImpl @Inject constructor(
    private val memberDataStore: MemberDataStore,
    private val mqttEventApi: MqttEventApi,
    private val roomDB: RoomDB,
): MqttEventClient {

    companion object {
        const val MEMBER_TOPIC = "mongs/member"
        const val MONG_TOPIC = "mongs/mong"
    }

    private var memberTopic = ""
    private var mongTopic = ""

    override suspend fun resetConnection() {
        memberTopic = ""
        mongTopic = ""
    }

    override suspend fun setConnection(accountId: Long) {
        mqttEventApi.connect(
            messageCallback = MessageEventCallback(
                setStarPoint = { starPoint ->
                    memberDataStore.setStarPoint(starPoint = starPoint)
                },
                updateMongCode = { mongId, mongCode ->
                    roomDB.slotDao().updateMongCodeByMqtt(mongId = mongId, mongCode = mongCode)
                },
                updateExp = { mongId, expPercent ->
                    roomDB.slotDao().updateExpByMqtt(mongId = mongId, exp = expPercent)
                },
                updateIsSleeping = { mongId, isSleeping ->
                    roomDB.slotDao()
                        .updateIsSleepingByMqtt(mongId = mongId, isSleeping = isSleeping)
                },
                updatePayPoint = { mongId, payPoint ->
                    roomDB.slotDao().updatePayPointByMqtt(mongId = mongId, payPoint = payPoint)
                },
                updatePoopCount = { mongId, poopCount ->
                    roomDB.slotDao().updatePoopCountByMqtt(mongId = mongId, poopCount = poopCount)
                },
                updateShiftCode = { mongId, shiftCode ->
                    roomDB.slotDao().updateShiftCodeByMqtt(
                        mongId = mongId,
                        shiftCode = Shift.valueOf(shiftCode).code
                    )
                },
                updateStateCode = { mongId, stateCode ->
                    roomDB.slotDao().updateStateCodeByMqtt(
                        mongId = mongId,
                        stateCode = State.valueOf(stateCode).code
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
        )
    }

    override suspend fun reconnect(resetMember: () -> Unit, resetSlot: () -> Unit) {
        mqttEventApi.connect(
            messageCallback = MessageEventCallback(
                setStarPoint = { starPoint ->
                    memberDataStore.setStarPoint(starPoint = starPoint)
                },
                updateMongCode = { mongId, mongCode ->
                    roomDB.slotDao().updateMongCodeByMqtt(mongId = mongId, mongCode = mongCode)
                },
                updateExp = { mongId, expPercent ->
                    roomDB.slotDao().updateExpByMqtt(mongId = mongId, exp = expPercent)
                },
                updateIsSleeping = { mongId, isSleeping ->
                    roomDB.slotDao()
                        .updateIsSleepingByMqtt(mongId = mongId, isSleeping = isSleeping)
                },
                updatePayPoint = { mongId, payPoint ->
                    roomDB.slotDao().updatePayPointByMqtt(mongId = mongId, payPoint = payPoint)
                },
                updatePoopCount = { mongId, poopCount ->
                    roomDB.slotDao().updatePoopCountByMqtt(mongId = mongId, poopCount = poopCount)
                },
                updateShiftCode = { mongId, shiftCode ->
                    roomDB.slotDao().updateShiftCodeByMqtt(
                        mongId = mongId,
                        shiftCode = Shift.valueOf(shiftCode).code
                    )
                },
                updateStateCode = { mongId, stateCode ->
                    roomDB.slotDao().updateStateCodeByMqtt(
                        mongId = mongId,
                        stateCode = State.valueOf(stateCode).code
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
        )
        if (memberTopic.isNotBlank()) {
            resetMember()
            mqttEventApi.subscribe(topic = memberTopic)
        }
        if (mongTopic.isNotBlank()) {
            resetSlot()
            mqttEventApi.subscribe(topic = mongTopic)
        }
    }
    override suspend fun disconnect() {
        if (memberTopic.isNotBlank()) {
            mqttEventApi.disSubscribe(topic = memberTopic)
        }
        if (mongTopic.isNotBlank()) {
            mqttEventApi.disSubscribe(topic = mongTopic)
        }
        mqttEventApi.disConnect()
    }

    override suspend fun subScribeMember(accountId: Long) {
        val newMemberTopic = "$MEMBER_TOPIC/$accountId"
        if (memberTopic != newMemberTopic) {
            this.disSubScribeMember()
            memberTopic = newMemberTopic
            mqttEventApi.subscribe(topic = memberTopic)
        }
    }

    override suspend fun disSubScribeMember() {
        if (memberTopic.isNotBlank()) {
            mqttEventApi.disSubscribe(topic = memberTopic)
            memberTopic = ""
        }
    }

    override suspend fun subScribeMong(mongId: Long) {
        val newMongTopic ="$MONG_TOPIC/$mongId"
        if (mongTopic != newMongTopic) {
            this.disSubScribeMong()
            mongTopic = newMongTopic
            mqttEventApi.subscribe(topic = mongTopic)
        }
    }

    override suspend fun disSubScribeMong() {
        if (mongTopic.isNotBlank()) {
            mqttEventApi.disSubscribe(topic = mongTopic)
            mongTopic = ""
        }
    }
}
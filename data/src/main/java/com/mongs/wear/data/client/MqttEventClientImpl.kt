package com.mongs.wear.data.client

import com.mongs.wear.data.api.client.MqttEventApi
import com.mongs.wear.data.callback.MessageEventCallback
import com.mongs.wear.data.dataStore.MemberDataStore
import com.mongs.wear.data.room.client.RoomDB
import com.mongs.wear.domain.client.MqttEventClient
import javax.inject.Inject

class MqttEventClientImpl @Inject constructor(
    private val memberDataStore: MemberDataStore,
    private val roomDB: RoomDB,
    private val mqttEventApi: MqttEventApi,
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
        mqttEventApi.connect(messageCallback = MessageEventCallback(
                memberDataStore = memberDataStore,
                roomDB = roomDB
            )
        )
    }

    override suspend fun reconnect(resetMember: () -> Unit, resetSlot: () -> Unit) {
        mqttEventApi.connect(messageCallback = MessageEventCallback(
                memberDataStore = memberDataStore,
                roomDB = roomDB
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
package com.mongs.wear.data.client

import android.util.Log
import com.mongs.wear.data.api.client.MqttApi
import com.mongs.wear.data.callback.MessageCallback
import com.mongs.wear.data.repository.RealTimeRepositoryImpl
import com.mongs.wear.domain.client.MqttClient
import javax.inject.Inject

class MqttClientImpl @Inject constructor(
    private val realTimeRepositoryImpl: RealTimeRepositoryImpl,
    private val mqttApi: MqttApi,
): MqttClient {

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
        mqttApi.connect(messageCallback = MessageCallback(realTimeRepositoryImpl = realTimeRepositoryImpl))
    }

    override suspend fun reconnect(resetMember: () -> Unit, resetSlot: () -> Unit) {
        mqttApi.connect(messageCallback = MessageCallback(realTimeRepositoryImpl = realTimeRepositoryImpl))
        if (memberTopic.isNotBlank()) {
            resetMember()
            mqttApi.subscribe(topic = memberTopic)
        }
        if (mongTopic.isNotBlank()) {
            resetSlot()
            mqttApi.subscribe(topic = mongTopic)
        }
    }
    override suspend fun disconnect() {
        if (memberTopic.isNotBlank()) {
            mqttApi.disSubscribe(topic = memberTopic)
        }
        if (mongTopic.isNotBlank()) {
            mqttApi.disSubscribe(topic = mongTopic)
        }
        mqttApi.disConnect()
    }

    override suspend fun subScribeMember(accountId: Long) {
        val newMemberTopic = "$MEMBER_TOPIC/$accountId"
        if (memberTopic != newMemberTopic) {
            this.disSubScribeMember()
            memberTopic = newMemberTopic
            mqttApi.subscribe(topic = memberTopic)
        }
    }

    override suspend fun disSubScribeMember() {
        if (memberTopic.isNotBlank()) {
            mqttApi.disSubscribe(topic = memberTopic)
        }
    }

    override suspend fun subScribeMong(mongId: Long) {
        val newMongTopic ="$MONG_TOPIC/$mongId"
        if (mongTopic != newMongTopic) {
            this.disSubScribeMong()
            mongTopic = newMongTopic
            mqttApi.subscribe(topic = mongTopic)
        }
    }

    override suspend fun disSubScribeMong() {
        if (mongTopic.isNotBlank()) {
            mqttApi.disSubscribe(topic = mongTopic)
        }
    }
}
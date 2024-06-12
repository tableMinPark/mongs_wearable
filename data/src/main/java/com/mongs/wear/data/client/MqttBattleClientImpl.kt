package com.mongs.wear.data.client

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mongs.wear.data.api.client.MqttBattleApi
import com.mongs.wear.data.callback.BattleMessageCallback
import com.mongs.wear.data.dataStore.BattleDataStore
import com.mongs.wear.data.utils.GsonDateFormatAdapter
import com.mongs.wear.domain.client.MqttBattleClient
import java.time.LocalDateTime
import javax.inject.Inject

class MqttBattleClientImpl @Inject constructor(
    private val battleDataStore: BattleDataStore,
    private val mqttApi: MqttBattleApi,
): MqttBattleClient {

    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, GsonDateFormatAdapter())
        .create()

    companion object {
        const val BATTLE_MATCH_TOPIC = "mongs/battle/match/search"
        const val BATTLE_ROUND_TOPIC = "mongs/battle/match/round"
    }

    private var battleMatchTopic = ""
    private var battleRoundTopic = ""

    override suspend fun setConnection() {
        mqttApi.connect(messageCallback =
            BattleMessageCallback(battleDataStore = battleDataStore)
        )
    }

    override suspend fun disconnect() {
        if (battleMatchTopic.isNotBlank()) {
            mqttApi.disSubscribe(topic = battleMatchTopic)
            battleMatchTopic = ""
        }
        if (battleRoundTopic.isNotBlank()) {
            mqttApi.disSubscribe(topic = battleRoundTopic)
            battleRoundTopic = ""
        }
        mqttApi.disConnect()
    }

    override suspend fun subScribeBattleMatch(mongId: Long) {
        val newBattleMatchTopic ="$BATTLE_MATCH_TOPIC/$mongId"
        if (battleMatchTopic != newBattleMatchTopic) {
            this.disSubScribeBattleMatch()
            battleMatchTopic = newBattleMatchTopic
            mqttApi.subscribe(topic = battleMatchTopic)
        }
    }

    override suspend fun disSubScribeBattleMatch() {
        if (battleMatchTopic.isNotBlank()) {
            mqttApi.disSubscribe(topic = battleMatchTopic)
            battleMatchTopic = ""
        }
    }

    override suspend fun produceBattleMatch(mongId: Long, data: Any) {
        if (battleMatchTopic.isNotBlank()) {
            val payload = gson.toJson(data)
            mqttApi.produce(topic = battleMatchTopic, payload = payload)
        }
    }

    override suspend fun subScribeBattleRound(roomId: String) {
        val newBattleRoundTopic ="$BATTLE_ROUND_TOPIC/$roomId"
        if (battleRoundTopic != newBattleRoundTopic) {
            this.disSubScribeBattleRound()
            battleRoundTopic = newBattleRoundTopic
            mqttApi.subscribe(topic = battleRoundTopic)
        }
    }

    override suspend fun disSubScribeBattleRound() {
        if (battleRoundTopic.isNotBlank()) {
            mqttApi.disSubscribe(topic = battleRoundTopic)
            battleRoundTopic = ""
        }
    }

    override suspend fun produceBattleRound(roomId: String, data: Any) {
        if (battleRoundTopic.isNotBlank()) {
            val payload = gson.toJson(data)
            mqttApi.produce(topic = battleRoundTopic, payload = payload)
        }
    }
}
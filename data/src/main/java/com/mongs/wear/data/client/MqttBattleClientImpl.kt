package com.mongs.wear.data.client

import android.content.Context
import com.google.gson.Gson
import com.mongs.wear.data.R
import com.mongs.wear.data.api.client.MqttBattleApi
import com.mongs.wear.data.api.code.PublishBattleCode
import com.mongs.wear.data.callback.MessageBattleCallback
import com.mongs.wear.data.code.BattlePick
import com.mongs.wear.data.code.MatchState
import com.mongs.wear.data.dto.mqttBattle.BasicBattlePublish
import com.mongs.wear.data.dto.mqttBattle.req.MatchEnterVo
import com.mongs.wear.data.dto.mqttBattle.req.MatchExitVo
import com.mongs.wear.data.dto.mqttBattle.req.MatchPickVo
import com.mongs.wear.data.room.client.RoomDB
import com.mongs.wear.data.room.entity.Match
import com.mongs.wear.data.room.entity.MatchPlayer
import com.mongs.wear.domain.client.MqttBattleClient
import com.mongs.wear.domain.code.BattlePickCode
import com.mongs.wear.domain.error.ClientErrorCode
import com.mongs.wear.domain.exception.ClientException
import org.eclipse.paho.client.mqttv3.MqttException
import javax.inject.Inject

class MqttBattleClientImpl @Inject constructor(
    private val context: Context,
    private val mqttApi: MqttBattleApi,
    private val roomDB: RoomDB,
    private val gson: Gson,
): MqttBattleClient {
    private val battleSearchBaseTopic = context.getString(R.string.mqtt_battle_search_base_topic)
    private val battleMatchBaseTopic = context.getString(R.string.mqtt_battle_match_base_topic)

    private var battleSearchTopic = ""
    private var battleMatchTopic = ""

    override suspend fun setConnection(
        matchFindCallback: suspend () -> Unit,
        matchEnterCallback: suspend () -> Unit,
    ) {
        try {
            mqttApi.connect(messageCallback = MessageBattleCallback(
                    gson = gson,
                    battleSearchBaseTopic = battleSearchBaseTopic,
                    battleMatchBaseTopic = battleMatchBaseTopic,
                    matchFind = { roomId, playerId ->
                        roomDB.matchDao().deleteAll()
                        roomDB.matchPlayerDao().deleteAll()
                        roomDB.matchDao().insert(
                            Match(
                                roomId = roomId,
                                round = 0,
                                myPlayerId = playerId,
                                matchState = MatchState.NONE
                            )
                        )
                        this.subScribeBattleMatch(roomId = roomId)
                        this.disSubScribeBattleSearch()
                        this.produceBattleMatchEnter(playerId = playerId, roomId = roomId)
                        matchFindCallback()
                    },
                    matchEnter = { matchVo ->
                        roomDB.matchDao().select()?.let { match ->
                            matchVo.matchPlayerVoList.map { matchPlayerVo ->
                                roomDB.matchPlayerDao().insert(
                                    MatchPlayer(
                                        playerId = matchPlayerVo.playerId,
                                        mongCode = matchPlayerVo.mongCode,
                                        hp = matchPlayerVo.hp,
                                        state = matchPlayerVo.state,
                                        roomId = matchVo.roomId,
                                        isMe = matchPlayerVo.playerId == match.myPlayerId,
                                    )
                                )
                            }
                            roomDB.matchDao().updateMatchState(
                                matchState = MatchState.ENTER,
                                roomId = matchVo.roomId,
                            )
                            matchEnterCallback()
                        }
                    },
                    match = { matchVo ->
                        roomDB.matchDao().updateMatch(
                            roomId = matchVo.roomId,
                            round = matchVo.round
                        )
                        matchVo.matchPlayerVoList.map { matchPlayerVo ->
                            roomDB.matchPlayerDao().updateMatchPlayer(
                                playerId = matchPlayerVo.playerId,
                                hp = matchPlayerVo.hp,
                                state = matchPlayerVo.state,
                            )
                        }
                        roomDB.matchDao().updateMatchState(
                            matchState = MatchState.MATCH,
                            roomId = matchVo.roomId,
                        )
                    },
                    matchOver = { matchOverVo ->
                        roomDB.matchDao().updateIsMatchOver(
                            isMatchOver = true,
                            roomId = matchOverVo.roomId,
                        )
                        roomDB.matchPlayerDao().updateMatchWinnerPlayer(
                            playerId = matchOverVo.winPlayer.playerId,
                        )
                        roomDB.matchDao().updateMatch(
                            roomId = matchOverVo.roomId,
                            round = matchOverVo.round
                        )
                        matchOverVo.matchPlayerVoList.map { matchPlayerVo ->
                            roomDB.matchPlayerDao().updateMatchPlayer(
                                playerId = matchPlayerVo.playerId,
                                hp = matchPlayerVo.hp,
                                state = matchPlayerVo.state,
                            )
                        }
                        roomDB.matchDao().updateMatchState(
                            matchState = MatchState.MATCH,
                            roomId = matchOverVo.roomId,
                        )
                    }
                )
            )
        } catch (e: MqttException) {
            throw ClientException(
                errorCode = ClientErrorCode.MQTT_BATTLE_CONNECT_FAIL,
                throwable = e,
            )
        }
    }
    override suspend fun disconnect() {
        try {
            if (battleSearchTopic.isNotBlank()) {
                mqttApi.disSubscribe(topic = battleSearchTopic)
                battleSearchTopic = ""
            }
            if (battleMatchTopic.isNotBlank()) {
                mqttApi.disSubscribe(topic = battleMatchTopic)
                battleMatchTopic = ""
            }
            mqttApi.disConnect()
        } catch (e: MqttException) {
            throw ClientException(
                errorCode = ClientErrorCode.MQTT_BATTLE_DISCONNECT_FAIL,
                throwable = e,
            )
        }
    }
    override suspend fun subScribeBattleSearch(deviceId: String) {
        try {
            val newBattleMatchTopic = "$battleSearchBaseTopic/$deviceId"
            if (battleSearchTopic != newBattleMatchTopic) {
                this.disSubScribeBattleSearch()
                battleSearchTopic = newBattleMatchTopic
                mqttApi.subscribe(topic = battleSearchTopic)
            }
        } catch (e: MqttException) {
            throw ClientException(
                errorCode = ClientErrorCode.MQTT_BATTLE_SUBSCRIBE_FAIL,
                throwable = e,
            )
        }
    }
    override suspend fun disSubScribeBattleSearch() {
        try {
            if (battleSearchTopic.isNotBlank()) {
                mqttApi.disSubscribe(topic = battleSearchTopic)
                battleSearchTopic = ""
            }
        } catch (e: MqttException) {
            throw ClientException(
                errorCode = ClientErrorCode.MQTT_BATTLE_UNSUBSCRIBE_FAIL,
                throwable = e,
            )
        }
    }
    override suspend fun subScribeBattleMatch(roomId: String) {
        try {
            val newBattleRoundTopic ="$battleMatchBaseTopic/$roomId"
            if (battleMatchTopic != newBattleRoundTopic) {
                this.disSubScribeBattleMatch()
                battleMatchTopic = newBattleRoundTopic
                mqttApi.subscribe(topic = battleMatchTopic)
            }
        } catch (e: MqttException) {
            throw ClientException(
                errorCode = ClientErrorCode.MQTT_BATTLE_SUBSCRIBE_FAIL,
                throwable = e,
            )
        }
    }
    override suspend fun disSubScribeBattleMatch() {
        try {
            if (battleMatchTopic.isNotBlank()) {
                mqttApi.disSubscribe(topic = battleMatchTopic)
                battleMatchTopic = ""
            }
        } catch (e: MqttException) {
            throw ClientException(
                errorCode = ClientErrorCode.MQTT_BATTLE_UNSUBSCRIBE_FAIL,
                throwable = e,
            )
        }
    }
    override suspend fun produceBattleMatchEnter(roomId: String, playerId: String) {
        try {
            if (battleMatchTopic.isNotBlank()) {
                val payload = gson.toJson(
                    BasicBattlePublish(
                        code = PublishBattleCode.MATCH_ENTER,
                        data = MatchEnterVo(
                            roomId = roomId,
                            playerId = playerId
                        )
                    )
                )
                mqttApi.produce(topic = battleMatchBaseTopic, payload = payload)
            }
        } catch (e: MqttException) {
            throw ClientException(
                errorCode = ClientErrorCode.MQTT_BATTLE_PRODUCE_FAIL,
                throwable = e,
            )
        }
    }
    override suspend fun produceBattleMatchPick(
        roomId: String,
        playerId: String,
        targetPlayerId: String,
        pickCode: BattlePickCode
    ) {
        try {
            if (battleMatchTopic.isNotBlank()) {
                val payload = gson.toJson(
                    BasicBattlePublish(
                        code = PublishBattleCode.MATCH_PICK,
                        data = MatchPickVo(
                            roomId = roomId,
                            playerId = playerId,
                            targetPlayerId = targetPlayerId,
                            pick = BattlePick.valueOf(pickCode.name)
                        )
                    )
                )
                mqttApi.produce(topic = battleMatchBaseTopic, payload = payload)
            }
        } catch (e: MqttException) {
            throw ClientException(
                errorCode = ClientErrorCode.MQTT_BATTLE_PRODUCE_FAIL,
                throwable = e,
            )
        }
    }
    override suspend fun produceBattleMatchExit(roomId: String, playerId: String) {
        try {
            if (battleMatchTopic.isNotBlank()) {
                val payload = gson.toJson(
                    BasicBattlePublish(
                        code = PublishBattleCode.MATCH_EXIT,
                        data = MatchExitVo(
                            roomId = roomId,
                            playerId = playerId,
                        )
                    )
                )
                mqttApi.produce(topic = battleMatchBaseTopic, payload = payload)
            }
        } catch (e: MqttException) {
            throw ClientException(
                errorCode = ClientErrorCode.MQTT_BATTLE_PRODUCE_FAIL,
                throwable = e,
            )
        }
    }
}
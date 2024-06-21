package com.mongs.wear.data.client

import com.google.gson.Gson
import com.google.gson.GsonBuilder
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
import com.mongs.wear.data.utils.GsonDateFormatAdapter
import com.mongs.wear.domain.client.MqttBattleClient
import com.mongs.wear.domain.code.BattlePickCode
import java.time.LocalDateTime
import javax.inject.Inject

class MqttBattleClientImpl @Inject constructor(
    private val mqttApi: MqttBattleApi,
    private val roomDB: RoomDB,
): MqttBattleClient {

    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, GsonDateFormatAdapter())
        .create()

    companion object {
        const val BATTLE_SEARCH_TOPIC = "mongs/battle/search"
        const val BATTLE_MATCH_TOPIC = "mongs/battle/match"
    }

    private var battleSearchTopic = ""
    private var battleMatchTopic = ""

    override suspend fun setConnection(
        matchFindCallback: suspend () -> Unit,
        matchEnterCallback: suspend () -> Unit,
    ) {
        mqttApi.connect(messageCallback =
            MessageBattleCallback(
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
    }

    override suspend fun disconnect() {
        if (battleSearchTopic.isNotBlank()) {
            mqttApi.disSubscribe(topic = battleSearchTopic)
            battleSearchTopic = ""
        }
        if (battleMatchTopic.isNotBlank()) {
            mqttApi.disSubscribe(topic = battleMatchTopic)
            battleMatchTopic = ""
        }
        mqttApi.disConnect()
    }

    override suspend fun subScribeBattleSearch(deviceId: String) {
        val newBattleMatchTopic ="$BATTLE_SEARCH_TOPIC/$deviceId"
        if (battleSearchTopic != newBattleMatchTopic) {
            this.disSubScribeBattleSearch()
            battleSearchTopic = newBattleMatchTopic
            mqttApi.subscribe(topic = battleSearchTopic)
        }
    }

    override suspend fun disSubScribeBattleSearch() {
        if (battleSearchTopic.isNotBlank()) {
            mqttApi.disSubscribe(topic = battleSearchTopic)
            battleSearchTopic = ""
        }
    }

    override suspend fun subScribeBattleMatch(roomId: String) {
        val newBattleRoundTopic ="$BATTLE_MATCH_TOPIC/$roomId"
        if (battleMatchTopic != newBattleRoundTopic) {
            this.disSubScribeBattleMatch()
            battleMatchTopic = newBattleRoundTopic
            mqttApi.subscribe(topic = battleMatchTopic)
        }
    }

    override suspend fun disSubScribeBattleMatch() {
        if (battleMatchTopic.isNotBlank()) {
            mqttApi.disSubscribe(topic = battleMatchTopic)
            battleMatchTopic = ""
        }
    }

    override suspend fun produceBattleMatchEnter(roomId: String, playerId: String) {
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
            mqttApi.produce(topic = BATTLE_MATCH_TOPIC, payload = payload)
        }
    }

    override suspend fun produceBattleMatchPick(
        roomId: String,
        playerId: String,
        targetPlayerId: String,
        pickCode: BattlePickCode
    ) {
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
            mqttApi.produce(topic = BATTLE_MATCH_TOPIC, payload = payload)
        }
    }

    override suspend fun produceBattleMatchExit(roomId: String, playerId: String) {
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
            mqttApi.produce(topic = BATTLE_MATCH_TOPIC, payload = payload)
        }
    }
}
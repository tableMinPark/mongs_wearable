package com.mongs.wear.data.client

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mongs.wear.data.api.client.MqttBattleApi
import com.mongs.wear.data.api.code.PublishBattleCode
import com.mongs.wear.data.callback.MessageBattleCallback
import com.mongs.wear.data.code.BattleState
import com.mongs.wear.data.code.MatchState
import com.mongs.wear.data.dto.mqttBattle.BasicBattlePublish
import com.mongs.wear.data.dto.mqttBattle.req.MatchEnterVo
import com.mongs.wear.data.dto.mqttBattle.req.MatchExitVo
import com.mongs.wear.data.room.client.RoomDB
import com.mongs.wear.data.room.entity.Match
import com.mongs.wear.data.room.entity.MatchPlayer
import com.mongs.wear.data.utils.GsonDateFormatAdapter
import com.mongs.wear.domain.client.MqttBattleClient
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

    ) {
        mqttApi.connect(messageCallback =
            MessageBattleCallback(
                matchFind = { roomId, playerId ->
                    // TODO("매치 구독, 입장 알림")
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
                    this.produceBattleMatchEnter(playerId = playerId, roomId = roomId)
                },
                matchEnter = { matchVo ->
                    // TODO("배틀 상태 초기화 및 등록")
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
                    }
                },
                match = { matchVo ->
                    // TODO("배틀 상태 업데이트")
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
                },
                matchOver = { matchOverVo ->
                    roomDB.matchPlayerDao().updateMatchWinnerPlayer(
                        playerId = matchOverVo.winPlayer.playerId,
                    )
                    roomDB.matchDao().updateMatchState(
                        matchState = MatchState.OVER,
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
            mqttApi.produce(topic = battleMatchTopic, payload = payload)
        }
    }

    override suspend fun produceBattleMatchExit(mongId: Long, roomId: String) {
        if (battleMatchTopic.isNotBlank()) {
            val payload = gson.toJson(
                BasicBattlePublish(
                    code = PublishBattleCode.MATCH_EXIT,
                    data = MatchExitVo(
                        mongId = mongId,
                        roomId = roomId,
                    )
                )
            )
            mqttApi.produce(topic = battleMatchTopic, payload = payload)
        }
    }
}
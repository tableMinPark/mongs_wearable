package com.mongs.wear.data.common.client

import android.content.Context
import com.mongs.wear.core.enums.MatchRoundCode
import com.mongs.wear.data.R
import com.mongs.wear.data.activity.dto.request.EnterBattleRequestDto
import com.mongs.wear.data.activity.dto.request.ExitBattleRequestDto
import com.mongs.wear.data.activity.dto.request.PickBattleRequestDto
import com.mongs.wear.data.common.api.MqttApi
import com.mongs.wear.data.common.exception.ConnectMqttException
import com.mongs.wear.data.common.exception.DisconnectMqttException
import com.mongs.wear.data.common.exception.PauseMqttException
import com.mongs.wear.data.common.exception.PubMqttException
import com.mongs.wear.data.common.exception.ResumeMqttException
import com.mongs.wear.data.common.exception.SubMqttException
import com.mongs.wear.domain.common.client.MqttClient
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject

class MqttClientImpl @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val mqttApi: MqttApi,
) : MqttClient {

    class MqttState {

        var broker = MqttStateCode.DISCONNECT

        var manager = MqttStateCode.DIS_SUB
        var managerTopic = ""

        var player = MqttStateCode.DIS_SUB
        var playerTopic = ""

        var searchMatch = MqttStateCode.DIS_SUB
        var searchMatchTopic = ""

        var battleMatch = MqttStateCode.DIS_SUB
        var battleMatchTopic = ""


        enum class MqttStateCode {
            CONNECT, PAUSE_DISCONNECT, DISCONNECT, SUB, PAUSE, DIS_SUB,
        }
    }

    companion object {
        val mqttState = MqttState()
    }

    override suspend fun isConnected(): Boolean {
        return mqttApi.isConnected()
    }

    override suspend fun isConnectPending(): Boolean {
        return mqttApi.isConnectPending();
    }

    /**
     * 일괄 처리
     */
    override suspend fun connect() {

        if (mqttState.broker == MqttState.MqttStateCode.DISCONNECT) {

            try {
                mqttState.broker = MqttState.MqttStateCode.CONNECT
                mqttApi.connect()

            } catch (e: Exception) {

                mqttState.broker = MqttState.MqttStateCode.DISCONNECT

                throw ConnectMqttException()
            }
        }
    }

    override suspend fun resumeConnect() {

        if (mqttState.broker == MqttState.MqttStateCode.PAUSE_DISCONNECT) {

            try {
                mqttState.broker = MqttState.MqttStateCode.CONNECT

                CoroutineScope(Dispatchers.IO).async {
                    mqttApi.connect()
                    resumeManager()
                    resumePlayer()
                    resumeSearchMatch()
                    resumeBattleMatch()
                }.await()

            } catch (e: Exception) {

                mqttState.broker = MqttState.MqttStateCode.PAUSE_DISCONNECT

                throw ResumeMqttException()
            }
        }
    }

    override suspend fun pauseConnect() {

        if (mqttState.broker == MqttState.MqttStateCode.CONNECT) {

            try {
                mqttState.broker = MqttState.MqttStateCode.PAUSE_DISCONNECT

                CoroutineScope(Dispatchers.IO).async {
                    pauseManager()
                    pausePlayer()
                    pauseSearchMatch()
                    pauseBattleMatch()
                    mqttApi.disConnect()
                }.await()

            } catch (_: Exception) {

                mqttState.broker = MqttState.MqttStateCode.CONNECT

                throw PauseMqttException()
            }
        }
    }

    override suspend fun disconnect() {

        if (mqttState.broker == MqttState.MqttStateCode.CONNECT) {

            try {
                mqttState.broker = MqttState.MqttStateCode.DISCONNECT

                CoroutineScope(Dispatchers.IO).async {
                    disSubManager()
                    disSubPlayer()
                    disSubSearchMatch()
                    disSubBattleMatch()
                    mqttApi.disConnect()
                }.await()

            } catch (_: Exception) {
                throw DisconnectMqttException()
            }
        }
    }

    /**
     * 매니저
     */
    override suspend fun subManager(mongId: Long) {

        if (mqttState.broker == MqttState.MqttStateCode.DISCONNECT) return

        try {
            if (mqttState.manager == MqttState.MqttStateCode.DIS_SUB) {
                mqttState.managerTopic =  "${context.getString(R.string.mqtt_manager_topic)}/$mongId"
                mqttApi.subscribe(mqttState.managerTopic)
                mqttState.manager = MqttState.MqttStateCode.SUB
            }
        } catch (_: Exception) {
            throw SubMqttException()
        }
    }

    override suspend fun resumeManager() {

        if (mqttState.broker == MqttState.MqttStateCode.DISCONNECT) return

        try {
            if (mqttState.manager == MqttState.MqttStateCode.PAUSE) {
                mqttApi.subscribe(mqttState.managerTopic)
                mqttState.manager = MqttState.MqttStateCode.SUB
            }
        } catch (_: Exception) {
            throw PauseMqttException()
        }
    }

    override suspend fun pauseManager() {

        if (mqttState.broker == MqttState.MqttStateCode.DISCONNECT) return

        try {
            if (mqttState.manager == MqttState.MqttStateCode.SUB) {
                mqttApi.disSubscribe(mqttState.managerTopic)
                mqttState.manager = MqttState.MqttStateCode.PAUSE
            }
        } catch (_: Exception) {
            throw PauseMqttException()
        }
    }

    override suspend fun disSubManager() {

        if (mqttState.broker == MqttState.MqttStateCode.DISCONNECT) return

        try {
            if (mqttState.manager == MqttState.MqttStateCode.SUB) {
                mqttApi.disSubscribe(mqttState.managerTopic)
                mqttState.managerTopic = ""
                mqttState.manager = MqttState.MqttStateCode.DIS_SUB
            }
        } catch (_: Exception) {
            throw PauseMqttException()
        }
    }

    /**
     * 배틀 매칭 대기열
     */
    override suspend fun subSearchMatch(deviceId: String) {

        if (mqttState.broker == MqttState.MqttStateCode.DISCONNECT) return

        try {
            if (mqttState.searchMatch == MqttState.MqttStateCode.DIS_SUB) {
                mqttState.searchMatchTopic = "${context.getString(R.string.mqtt_battle_search_topic)}/$deviceId"
                mqttApi.subscribe(mqttState.searchMatchTopic)
                mqttState.searchMatch = MqttState.MqttStateCode.SUB
            }
        } catch (_: Exception) {
            throw SubMqttException()
        }
    }

    override suspend fun resumeSearchMatch() {

        if (mqttState.broker == MqttState.MqttStateCode.DISCONNECT) return

        try {
            if (mqttState.searchMatch == MqttState.MqttStateCode.PAUSE) {
                mqttApi.subscribe(mqttState.searchMatchTopic)
                mqttState.searchMatch = MqttState.MqttStateCode.SUB
            }
        } catch (_: Exception) {
            throw PauseMqttException()
        }
    }

    override suspend fun pauseSearchMatch() {

        if (mqttState.broker == MqttState.MqttStateCode.DISCONNECT) return

        try {
            if (mqttState.searchMatch == MqttState.MqttStateCode.SUB) {
                mqttApi.disSubscribe(mqttState.searchMatchTopic)
                mqttState.searchMatch = MqttState.MqttStateCode.PAUSE
            }
        } catch (_: Exception) {
            throw PauseMqttException()
        }
    }

    override suspend fun disSubSearchMatch() {

        if (mqttState.broker == MqttState.MqttStateCode.DISCONNECT) return

        try {
            if (mqttState.searchMatch == MqttState.MqttStateCode.SUB) {
                mqttApi.disSubscribe(mqttState.searchMatchTopic)
                mqttState.searchMatchTopic = ""
                mqttState.searchMatch = MqttState.MqttStateCode.DIS_SUB
            }
        } catch (_: Exception) {
            throw PauseMqttException()
        }
    }

    /**
     * 배틀 매치
     */
    override suspend fun subBattleMatch(roomId: Long) {

        if (mqttState.broker == MqttState.MqttStateCode.DISCONNECT) return

        try {
            if (mqttState.battleMatch == MqttState.MqttStateCode.DIS_SUB) {
                mqttState.battleMatchTopic = "${context.getString(R.string.mqtt_battle_match_topic)}/$roomId"
                mqttApi.subscribe(mqttState.battleMatchTopic)
                mqttState.battleMatch = MqttState.MqttStateCode.SUB
            }
        } catch (_: Exception) {
            throw SubMqttException()
        }
    }

    override suspend fun pubBattleMatchEnter(roomId: Long, playerId: String) {

        if (mqttState.broker == MqttState.MqttStateCode.DISCONNECT) return

        try {

            val topic = "${context.getString(R.string.mqtt_battle_match_topic)}/$roomId/enter"

            mqttApi.produce(topic = topic, requestDto = EnterBattleRequestDto(playerId = playerId))

        } catch (_: Exception) {
            throw PubMqttException()
        }
    }

    override suspend fun pubBattleMatchPick(roomId: Long, playerId: String, targetPlayerId: String, pickCode: MatchRoundCode) {

        if (mqttState.broker == MqttState.MqttStateCode.DISCONNECT) return

        try {

            val topic = "${context.getString(R.string.mqtt_battle_match_topic)}/$roomId/pick"

            mqttApi.produce(topic = topic, requestDto = PickBattleRequestDto(playerId = playerId, targetPlayerId = targetPlayerId, pickCode = pickCode))

        } catch (_: Exception) {
            throw PubMqttException()
        }
    }

    override suspend fun pubBattleMatchExit(roomId: Long, playerId: String) {

        if (mqttState.broker == MqttState.MqttStateCode.DISCONNECT) return

        try {

            val topic = "${context.getString(R.string.mqtt_battle_match_topic)}/$roomId/enter"

            mqttApi.produce(topic = topic, requestDto = ExitBattleRequestDto(playerId = playerId))

        } catch (_: Exception) {
            throw PubMqttException()
        }
    }

    override suspend fun resumeBattleMatch() {

        if (mqttState.broker == MqttState.MqttStateCode.DISCONNECT) return

        try {
            if (mqttState.battleMatch == MqttState.MqttStateCode.PAUSE) {
                mqttApi.subscribe(mqttState.battleMatchTopic)
                mqttState.battleMatch = MqttState.MqttStateCode.SUB
            }
        } catch (_: Exception) {
            throw PauseMqttException()
        }
    }

    override suspend fun pauseBattleMatch() {

        if (mqttState.broker == MqttState.MqttStateCode.DISCONNECT) return

        try {
            if (mqttState.battleMatch == MqttState.MqttStateCode.SUB) {
                mqttApi.disSubscribe(mqttState.battleMatchTopic)
                mqttState.battleMatch = MqttState.MqttStateCode.PAUSE
            }
        } catch (_: Exception) {
            throw PauseMqttException()
        }
    }

    override suspend fun disSubBattleMatch() {

        if (mqttState.broker == MqttState.MqttStateCode.DISCONNECT) return

        try {
            if (mqttState.battleMatch == MqttState.MqttStateCode.SUB) {
                mqttApi.disSubscribe(mqttState.battleMatchTopic)
                mqttState.battleMatchTopic = ""
                mqttState.battleMatch = MqttState.MqttStateCode.DIS_SUB
            }
        } catch (_: Exception) {
            throw PauseMqttException()
        }
    }

    /**
     * 플레이어
     */
    override suspend fun subPlayer(accountId: Long) {

        if (mqttState.broker == MqttState.MqttStateCode.DISCONNECT) return

        try {
            if (mqttState.player == MqttState.MqttStateCode.DIS_SUB) {
                mqttState.playerTopic = "${context.getString(R.string.mqtt_player_topic)}/$accountId"
                mqttApi.subscribe(mqttState.playerTopic)
                mqttState.player = MqttState.MqttStateCode.SUB
            }
        } catch (_: Exception) {
            throw SubMqttException()
        }
    }

    override suspend fun resumePlayer() {

        if (mqttState.broker == MqttState.MqttStateCode.DISCONNECT) return

        try {
            if (mqttState.player == MqttState.MqttStateCode.PAUSE) {
                mqttApi.subscribe(mqttState.playerTopic)
                mqttState.player = MqttState.MqttStateCode.SUB
            }
        } catch (_: Exception) {
            throw PauseMqttException()
        }
    }

    override suspend fun pausePlayer() {

        if (mqttState.broker == MqttState.MqttStateCode.DISCONNECT) return

        try {
            if (mqttState.player == MqttState.MqttStateCode.SUB) {
                mqttApi.disSubscribe(mqttState.playerTopic)
                mqttState.player = MqttState.MqttStateCode.PAUSE
            }
        } catch (_: Exception) {
            throw PauseMqttException()
        }
    }

    override suspend fun disSubPlayer() {

        if (mqttState.broker == MqttState.MqttStateCode.DISCONNECT) return

        try {
            if (mqttState.player == MqttState.MqttStateCode.SUB) {
                mqttApi.disSubscribe(mqttState.playerTopic)
                mqttState.playerTopic = ""
                mqttState.player = MqttState.MqttStateCode.DIS_SUB
            }
        } catch (_: Exception) {
            throw PauseMqttException()
        }
    }
}
package com.mongs.wear.domain.client

import com.mongs.wear.domain.code.BattlePickCode

interface MqttBattleClient {
    suspend fun setConnection(
        matchFindCallback: suspend () -> Unit,
        matchEnterCallback: suspend () -> Unit,
    )
    suspend fun disconnect()
    suspend fun subScribeBattleSearch(deviceId: String)
    suspend fun disSubScribeBattleSearch()
    suspend fun subScribeBattleMatch(roomId: String)
    suspend fun disSubScribeBattleMatch()
    suspend fun produceBattleMatchEnter(roomId: String, playerId: String)
    suspend fun produceBattleMatchPick(roomId: String, playerId: String, targetPlayerId: String, pickCode: BattlePickCode)
    suspend fun produceBattleMatchExit(roomId: String, playerId: String)
}
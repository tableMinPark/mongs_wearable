package com.mongs.wear.domain.client

interface MqttBattleClient {
    suspend fun setConnection()
    suspend fun disconnect()
    suspend fun subScribeBattleSearch(deviceId: String)
    suspend fun disSubScribeBattleSearch()
    suspend fun subScribeBattleMatch(roomId: String)
    suspend fun disSubScribeBattleMatch()
    suspend fun produceBattleMatchEnter(roomId: String, playerId: String)
    suspend fun produceBattleMatchExit(mongId: Long, roomId: String)
}
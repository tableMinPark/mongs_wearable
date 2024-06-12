package com.mongs.wear.domain.client

interface MqttBattleClient {
    suspend fun setConnection()
    suspend fun disconnect()
    suspend fun subScribeBattleMatch(mongId: Long)
    suspend fun disSubScribeBattleMatch()
    suspend fun produceBattleMatch(mongId: Long, data: Any)
    suspend fun subScribeBattleRound(roomId: String)
    suspend fun disSubScribeBattleRound()
    suspend fun produceBattleRound(roomId: String, data: Any)
}
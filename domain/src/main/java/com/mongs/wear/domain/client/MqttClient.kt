package com.mongs.wear.domain.client

interface MqttClient {

    suspend fun setConnection()

    suspend fun reconnect()

    suspend fun pauseConnect()

    suspend fun disconnect()

    suspend fun subManager()

    suspend fun disSubManager()

    suspend fun subSearchMatch()

    suspend fun disSubSearchMatch()

    suspend fun subBattleMatch()

    suspend fun disSubBattleMatch()

    suspend fun subPlayer(accountId: Long)

    suspend fun disSubPlayer()
}
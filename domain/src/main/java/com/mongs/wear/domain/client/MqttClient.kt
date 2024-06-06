package com.mongs.wear.domain.client

interface MqttClient {
    suspend fun setConnection(accountId: Long)
    suspend fun reconnect()
    suspend fun disconnect()
    suspend fun resetConnection()
    suspend fun subScribeMong(pastMongId: Long = -1L, mongId: Long)
}
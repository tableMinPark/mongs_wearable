package com.mongs.wear.domain.client

interface MqttClient {
    suspend fun setConnection(accountId: Long)
    suspend fun reconnect(resetMember: () -> Unit, resetSlot: () -> Unit)
    suspend fun disconnect()
    suspend fun resetConnection()
    suspend fun subScribeMember(accountId: Long)
    suspend fun disSubScribeMember()
    suspend fun subScribeMong(mongId: Long)
    suspend fun disSubScribeMong()
}
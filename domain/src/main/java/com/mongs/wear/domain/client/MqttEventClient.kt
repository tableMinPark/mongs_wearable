package com.mongs.wear.domain.client

interface MqttEventClient {
    suspend fun setConnection()
    suspend fun reconnect(resetMember: () -> Unit, resetSlot: () -> Unit)
    suspend fun pauseConnect()
    suspend fun disconnect()
    suspend fun subScribeMember(accountId: Long)
    suspend fun disSubScribeMember()
    suspend fun subScribeMong(mongId: Long)
    suspend fun disSubScribeMong()
}
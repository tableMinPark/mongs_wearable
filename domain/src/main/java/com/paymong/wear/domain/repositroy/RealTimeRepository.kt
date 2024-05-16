package com.paymong.wear.domain.repositroy

interface RealTimeRepository {
    suspend fun setConnection(accountId: Long)
    suspend fun reconnect()
    suspend fun disconnect()
    suspend fun resetConnection()
}
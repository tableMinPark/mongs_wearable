package com.paymong.wear.domain.refac.repositroy

interface RealTimeRepository {
    suspend fun setConnection(accountId: Long)
    suspend fun disconnect()
    suspend fun resetConnection()
}
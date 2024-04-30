package com.paymong.wear.domain.repository.notification

interface NotificationRepository {
    suspend fun initializeNotification(accountId: Long)
    suspend fun reconnectNotification()
    suspend fun disconnectNotification()
    suspend fun resetNotification()
}
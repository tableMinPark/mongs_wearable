package com.paymong.wear.domain.repository.auth

interface AuthRepository {
    suspend fun login(deviceId: String, email: String, name: String): Long
    suspend fun reissue()
    suspend fun logout(): Long
}
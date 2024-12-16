package com.mongs.wear.domain.repositroy

interface AuthRepository {

    suspend fun login(deviceId: String, email: String, name: String) : Long

    suspend fun logout()

    suspend fun reissue() : String

    suspend fun getAccessToken(): String
}
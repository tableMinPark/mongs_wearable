package com.paymong.wear.domain.refac.repositroy

interface DeviceRepository {
    suspend fun getDeviceId() : String
    suspend fun setRefreshToken(refreshToken: String)
    suspend fun getRefreshToken() : String
    suspend fun setAccessToken(accessToken: String)
    suspend fun getAccessToken() : String
    suspend fun getCodeIntegrity() : String
    suspend fun getBuildVersion() : String
}
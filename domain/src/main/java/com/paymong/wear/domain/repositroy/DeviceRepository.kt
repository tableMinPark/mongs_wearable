package com.paymong.wear.domain.repositroy

interface DeviceRepository {
    suspend fun setDeviceId(deviceId: String)
    suspend fun getDeviceId(): String

    suspend fun setRefreshToken(refreshToken: String)
    suspend fun getRefreshToken(): String

    suspend fun setAccessToken(accessToken: String)
    suspend fun getAccessToken(): String

    suspend fun setCodeIntegrity(codeIntegrity: String)
    suspend fun getCodeIntegrity(): String

    suspend fun setBuildVersion(buildVersion: String)
    suspend fun getBuildVersion(): String

    suspend fun setBackgroundMapCode(code: String)
    suspend fun getBackgroundMapCode(): String
}
package com.mongs.wear.domain.repositroy

import androidx.lifecycle.LiveData

interface DeviceRepository {
    suspend fun setBuildVersion(buildVersion: String)
    suspend fun getBuildVersion(): String
    suspend fun setCodeIntegrity(codeIntegrity: String)
    suspend fun getCodeIntegrity(): String
    suspend fun setDeviceId(deviceId: String)
    suspend fun getDeviceId(): String
    suspend fun setBackgroundMapCode(code: String)
    suspend fun getBackgroundMapCode(): LiveData<String>
}
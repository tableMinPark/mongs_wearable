package com.mongs.wear.domain.repositroy

import androidx.lifecycle.LiveData
import java.time.LocalDateTime

interface DeviceRepository {
    suspend fun setNetworkFlag(networkFlag: Boolean)
    suspend fun getNetworkFlagLive(): LiveData<Boolean>
    suspend fun setBuildVersion(buildVersion: String)
    suspend fun getBuildVersion(): String
    suspend fun setCodeIntegrity(codeIntegrity: String)
    suspend fun getCodeIntegrity(): String
    suspend fun setDeviceId(deviceId: String)
    suspend fun getDeviceId(): String
    suspend fun setBackgroundMapCode(code: String)
    suspend fun getBackgroundMapCode(): LiveData<String>
    suspend fun setUpTime(upTime: LocalDateTime)
    suspend fun getUpTime(): LocalDateTime
    suspend fun setRebootFlag(rebootFlag: Boolean)
    suspend fun getRebootFlag(): Boolean
}
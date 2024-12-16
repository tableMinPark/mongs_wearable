package com.mongs.wear.domain.repositroy

import androidx.lifecycle.LiveData
import java.time.LocalDateTime

interface DeviceRepository {

    suspend fun setBuildVersion(buildVersion: String)

    suspend fun setDeviceId(deviceId: String)

    suspend fun getDeviceId(): String

    suspend fun setBackgroundMapCode(code: String)

    suspend fun getBackgroundMapCode(): LiveData<String>

    suspend fun setRebootFlag(rebootFlag: Boolean)

    suspend fun getRebootFlag(): Boolean
}
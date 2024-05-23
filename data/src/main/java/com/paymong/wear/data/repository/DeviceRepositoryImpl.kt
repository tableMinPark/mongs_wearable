package com.paymong.wear.data.repository

import androidx.lifecycle.LiveData
import com.paymong.wear.data.dataStore.DeviceDataStore
import com.paymong.wear.data.dataStore.MemberDataStore
import com.paymong.wear.domain.repositroy.DeviceRepository
import javax.inject.Inject

class DeviceRepositoryImpl @Inject constructor(
    private val memberDataStore: MemberDataStore,
    private val deviceDataStore: DeviceDataStore,
): DeviceRepository {
    override suspend fun setBuildVersion(buildVersion: String) {
        deviceDataStore.setBuildVersion(buildVersion = buildVersion)
    }
    override suspend fun getBuildVersion(): String {
        return deviceDataStore.getBuildVersion()
    }
    override suspend fun setCodeIntegrity(codeIntegrity: String) {
        deviceDataStore.setCodeIntegrity(codeIntegrity = codeIntegrity)
    }
    override suspend fun getCodeIntegrity(): String {
        return deviceDataStore.getCodeIntegrity()
    }
    override suspend fun setDeviceId(deviceId: String) {
        deviceDataStore.setDeviceId(deviceId = deviceId)
    }
    override suspend fun getDeviceId(): String {
        return deviceDataStore.getDeviceId()
    }
    override suspend fun setBackgroundMapCode(code: String) {
        deviceDataStore.setBackgroundMapCode(backgroundMapCode = code)
    }
    override suspend fun getBackgroundMapCode(): LiveData<String> {
        return deviceDataStore.getBackgroundMapCodeLive()
    }
}
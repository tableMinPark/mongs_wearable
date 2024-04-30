package com.paymong.wear.data.repository.common

import androidx.lifecycle.LiveData
import com.paymong.wear.data.dataStore.device.DeviceDataStore
import com.paymong.wear.domain.repository.common.DeviceRepository
import javax.inject.Inject

class DeviceRepositoryImpl @Inject constructor(
    private val deviceDataStore: DeviceDataStore
) : DeviceRepository {
    override suspend fun setBuildVersion(buildVersion: String) {
        deviceDataStore.modifyBuildVersion(buildVersion = buildVersion)
    }

    override suspend fun getDeviceId(): String {
        return deviceDataStore.findDeviceId()
    }

    override suspend fun setNetworkFlag(networkFlag: Boolean) {
        deviceDataStore.modifyNetworkFlag(networkFlag = networkFlag)
    }

    override suspend fun getNetworkFlag(): LiveData<Boolean> {
        return deviceDataStore.findNetworkFlag()
    }
}
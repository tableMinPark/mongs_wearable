package com.mongs.wear.data.device.repository

import androidx.lifecycle.LiveData
import com.mongs.wear.data.device.datastore.DeviceDataStore
import com.mongs.wear.domain.device.repository.DeviceRepository
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceRepositoryImpl @Inject constructor(
    private val deviceDataStore: DeviceDataStore,
) : DeviceRepository {

    override suspend fun setDeviceId(deviceId: String) {

        if (deviceId.isEmpty() || "unknown" == deviceId) {
            deviceDataStore.setDeviceId(deviceId = UUID.randomUUID().toString().replace("-", ""))
            return
        }

        deviceDataStore.setDeviceId(deviceId = deviceId)
    }

    override suspend fun getDeviceId(): String = deviceDataStore.getDeviceId()

    override suspend fun setDeviceBootedDt(deviceBootedDt: LocalDateTime) {
        deviceDataStore.setDeviceBootedDt(deviceBootedDt = deviceBootedDt)
    }

    override suspend fun getDeviceBootedDt(): LocalDateTime = deviceDataStore.getDeviceBootedDt()

    override suspend fun setBgMapTypeCode(mapTypeCode: String) {
        deviceDataStore.setBgMapTypeCode(mapTypeCode = mapTypeCode)
    }

    override suspend fun getBgMapTypeCodeLive(): LiveData<String> {
        return deviceDataStore.getBgMapTypeCodeLive()
    }

    override suspend fun setNetwork(network: Boolean) {
        deviceDataStore.setNetwork(network = network)
    }

    override suspend fun getNetworkLive(): LiveData<Boolean> {
        return deviceDataStore.getNetworkLive()
    }
}
package com.mongs.wear.data.common.repository

import androidx.lifecycle.LiveData
import com.mongs.wear.data.common.datastore.AppDataStore
import com.mongs.wear.domain.common.repository.AppRepository
import java.time.LocalDateTime
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val appDataStore: AppDataStore,
) : AppRepository {

    override suspend fun getDeviceId(): String = appDataStore.getDeviceId()

    override suspend fun setDeviceBootedDt(deviceBootedDt: LocalDateTime) {
        appDataStore.setDeviceBootedDt(deviceBootedDt = deviceBootedDt)
    }

    override suspend fun getDeviceBootedDt(): LocalDateTime = appDataStore.getDeviceBootedDt()

    override suspend fun setBgMapTypeCode(mapTypeCode: String) {
        appDataStore.setBgMapTypeCode(mapTypeCode = mapTypeCode)
    }

    override suspend fun getBgMapTypeCodeLive(): LiveData<String> {
        return appDataStore.getBgMapTypeCodeLive()
    }

    override suspend fun setNetwork(network: Boolean) {
        appDataStore.setNetwork(network = network)
    }

    override suspend fun getNetworkLive(): LiveData<Boolean> {
        return appDataStore.getNetworkLive()
    }
}
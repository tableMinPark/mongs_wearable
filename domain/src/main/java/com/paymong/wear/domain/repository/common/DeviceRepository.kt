package com.paymong.wear.domain.repository.common

import androidx.lifecycle.LiveData

interface DeviceRepository {
    suspend fun setBuildVersion(buildVersion: String)
    suspend fun getDeviceId(): String
    suspend fun setNetworkFlag(networkFlag: Boolean)
    suspend fun getNetworkFlag(): LiveData<Boolean>
}
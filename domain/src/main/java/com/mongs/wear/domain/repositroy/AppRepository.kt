package com.mongs.wear.domain.repositroy

import androidx.lifecycle.LiveData

interface AppRepository {

    suspend fun getDeviceId(): String

    suspend fun setDeviceName(deviceName: String)

    suspend fun getDeviceName(): String

    suspend fun getAppCode(): String

    suspend fun setBuildVersion(buildVersion: String)

    suspend fun getBuildVersion(): String

    suspend fun setBgMapTypeCode(mapTypeCode: String)

    suspend fun getBgMapTypeCodeLive(): LiveData<String>

    suspend fun setNetwork(network: Boolean)

    suspend fun getNetworkLive(): LiveData<Boolean>
}
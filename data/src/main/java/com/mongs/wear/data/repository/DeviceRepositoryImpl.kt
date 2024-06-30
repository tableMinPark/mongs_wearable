package com.mongs.wear.data.repository

import androidx.lifecycle.LiveData
import com.mongs.wear.data.dataStore.DeviceDataStore
import com.mongs.wear.domain.error.RepositoryErrorCode
import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.repositroy.DeviceRepository
import javax.inject.Inject

class DeviceRepositoryImpl @Inject constructor(
    private val deviceDataStore: DeviceDataStore,
): DeviceRepository {
    override suspend fun setNetworkFlag(networkFlag: Boolean) {
        try {
            deviceDataStore.setNetworkFlag(networkFlag = networkFlag)
        } catch (e: RuntimeException) {
            throw RepositoryException(
                errorCode = RepositoryErrorCode.SET_NETWORK_FLAG_FAIL,
                throwable = e,
            )
        }
    }
    override suspend fun getNetworkFlagLive(): LiveData<Boolean> {
        try {
            return deviceDataStore.getNetworkFlagLive()
        } catch (e: RuntimeException) {
            throw RepositoryException(
                errorCode = RepositoryErrorCode.GET_NETWORK_FLAG_FAIL,
                throwable = e,
            )
        }
    }
    override suspend fun setBuildVersion(buildVersion: String) {
        try {
            deviceDataStore.setBuildVersion(buildVersion = buildVersion)
        } catch (e: RuntimeException) {
            throw RepositoryException(
                errorCode = RepositoryErrorCode.SET_BUILD_VERSION_FAIL,
                throwable = e,
            )
        }
    }
    override suspend fun getBuildVersion(): String {
        try {
            return deviceDataStore.getBuildVersion()
        } catch (e: RuntimeException) {
            throw RepositoryException(
                errorCode = RepositoryErrorCode.GET_BUILD_VERSION_FAIL,
                throwable = e,
            )
        }
    }
    override suspend fun setCodeIntegrity(codeIntegrity: String) {
        try {
            deviceDataStore.setCodeIntegrity(codeIntegrity = codeIntegrity)
        } catch (e: RuntimeException) {
            throw RepositoryException(
                errorCode = RepositoryErrorCode.SET_CODE_INTEGRITY_FAIL,
                throwable = e,
            )
        }
    }
    override suspend fun getCodeIntegrity(): String {
        try {
            return deviceDataStore.getCodeIntegrity()
        } catch (e: RuntimeException) {
            throw RepositoryException(
                errorCode = RepositoryErrorCode.GET_CODE_INTEGRITY_FAIL,
                throwable = e,
            )
        }
    }
    override suspend fun setDeviceId(deviceId: String) {
        try {
            deviceDataStore.setDeviceId(deviceId = deviceId)
        } catch (e: RuntimeException) {
            throw RepositoryException(
                errorCode = RepositoryErrorCode.SET_DEVICE_ID_FAIL,
                throwable = e,
            )
        }
    }
    override suspend fun getDeviceId(): String {
        try {
            return deviceDataStore.getDeviceId()
        } catch (e: RuntimeException) {
            throw RepositoryException(
                errorCode = RepositoryErrorCode.GET_DEVICE_ID_FAIL,
                throwable = e,
            )
        }
    }
    override suspend fun setBackgroundMapCode(code: String) {
        try {
            deviceDataStore.setBackgroundMapCode(backgroundMapCode = code)
        } catch (e: RuntimeException) {
            throw RepositoryException(
                errorCode = RepositoryErrorCode.SET_BACKGROUND_MAP_CODE_FAIL,
                throwable = e,
            )
        }
    }
    override suspend fun getBackgroundMapCode(): LiveData<String> {
        try {
            return deviceDataStore.getBackgroundMapCodeLive()
        } catch (e: RuntimeException) {
            throw RepositoryException(
                errorCode = RepositoryErrorCode.GET_BACKGROUND_MAP_CODE_FAIL,
                throwable = e,
            )
        }
    }
}
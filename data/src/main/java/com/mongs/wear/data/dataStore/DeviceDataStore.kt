package com.mongs.wear.data.dataStore

import android.content.Context
import android.icu.text.DateFormat
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID
import javax.inject.Inject

class DeviceDataStore @Inject constructor(
    private val context: Context
)  {
    private val Context.device  by preferencesDataStore(name = "DEVICE")

    companion object {
        private val NETWORK_FLAG = booleanPreferencesKey("NETWORK_FLAG")
        private val BUILD_VERSION = stringPreferencesKey("BUILD_VERSION")
        private val CODE_INTEGRITY = stringPreferencesKey("CODE_INTEGRITY")
        private val DEVICE_ID = stringPreferencesKey("DEVICE_ID")
        private val BACKGROUND_MAP_CODE = stringPreferencesKey("BACKGROUND_MAP_CODE")
        private val UP_TIME = stringPreferencesKey("UP_TIME")
        private val REBOOT_FLAG = booleanPreferencesKey("REBOOT_FLAG")

        private val dateFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
    }

    init {
        CoroutineScope(Dispatchers.IO).launch {
            context.device.edit { preferences ->
                if (!preferences.contains(NETWORK_FLAG)) {
                    preferences[NETWORK_FLAG] = true
                }
                if (!preferences.contains(BUILD_VERSION)) {
                    preferences[BUILD_VERSION] = "1.0.0"
                }
                if (!preferences.contains(CODE_INTEGRITY)) {
                    preferences[CODE_INTEGRITY] = ""
                }
                if (!preferences.contains(DEVICE_ID)) {
                    preferences[DEVICE_ID] = UUID.randomUUID().toString().replace("-", "")
                }
                if (!preferences.contains(BACKGROUND_MAP_CODE)) {
                    preferences[BACKGROUND_MAP_CODE] = "MP000"
                }
                if (!preferences.contains(UP_TIME)) {
                    preferences[UP_TIME] = LocalDateTime.of(1970, 1, 1, 0, 0, 0).format(dateFormatter)
                }
                if (!preferences.contains(REBOOT_FLAG)) {
                    preferences[REBOOT_FLAG] = true
                }
            }
        }
    }

    suspend fun setNetworkFlag(networkFlag: Boolean) {
        context.device.edit { preferences ->
            preferences[NETWORK_FLAG] = networkFlag
        }
    }
    fun getNetworkFlagLive(): LiveData<Boolean> {
        return context.device.data.map { preferences ->
            preferences[NETWORK_FLAG]!!
        }.asLiveData()
    }
    suspend fun setBuildVersion(buildVersion: String) {
        context.device.edit { preferences ->
            preferences[BUILD_VERSION] = buildVersion
        }
    }
    suspend fun getBuildVersion(): String {
        return runBlocking {
            context.device.data.map { preferences ->
                preferences[BUILD_VERSION]!!
            }.first()
        }
    }
    suspend fun setCodeIntegrity(codeIntegrity: String) {
        context.device.edit { preferences ->
            preferences[CODE_INTEGRITY] = codeIntegrity
        }
    }
    suspend fun getCodeIntegrity(): String {
        return runBlocking {
            context.device.data.map { preferences ->
                preferences[CODE_INTEGRITY]!!
            }.first()
        }
    }
    suspend fun setDeviceId(deviceId: String) {
        context.device.edit { preferences ->
            preferences[DEVICE_ID] = deviceId
        }
    }
    suspend fun getDeviceId(): String {
        return context.device.data.map { preferences ->
            preferences[DEVICE_ID]!!
        }.first()
    }
    suspend fun setBackgroundMapCode(backgroundMapCode: String) {
        context.device.edit { preferences ->
            preferences[BACKGROUND_MAP_CODE] = backgroundMapCode
        }
    }
    fun getBackgroundMapCodeLive(): LiveData<String> {
        return context.device.data.map { preferences ->
            preferences[BACKGROUND_MAP_CODE]!!
        }.asLiveData()
    }
    suspend fun setUpTime(upTime: LocalDateTime) {
        context.device.edit { preferences ->
            preferences[UP_TIME] = upTime.format(dateFormatter)
        }
    }
    fun getUpTime(): LocalDateTime {
        return runBlocking {
            context.device.data.map { preferences ->
                LocalDateTime.parse(preferences[UP_TIME]!!, dateFormatter)
            }.first()
        }
    }
    suspend fun setRebootFlag(rebootFlag: Boolean) {
        context.device.edit { preferences ->
            preferences[REBOOT_FLAG] = rebootFlag
        }
    }
    fun getRebootFlag(): Boolean {
        return runBlocking {
            context.device.data.map { preferences ->
                preferences[REBOOT_FLAG]!!
            }.first()
        }
    }
}
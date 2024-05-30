package com.paymong.wear.data.dataStore

import android.content.Context
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
import java.util.UUID
import javax.inject.Inject

class DeviceDataStore @Inject constructor(
    private val context: Context
)  {
    private val Context.device  by preferencesDataStore(name = "DEVICE")

    companion object {
        private val BUILD_VERSION = stringPreferencesKey("BUILD_VERSION")
        private val CODE_INTEGRITY = stringPreferencesKey("CODE_INTEGRITY")
        private val DEVICE_ID = stringPreferencesKey("DEVICE_ID")
        private val BACKGROUND_MAP_CODE = stringPreferencesKey("BACKGROUND_MAP_CODE")
    }

    init {
        CoroutineScope(Dispatchers.IO).launch {
            context.device.edit { preferences ->
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
            }
        }
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
    suspend fun getBackgroundMapCodeLive(): LiveData<String> {
        return context.device.data.map { preferences ->
            preferences[BACKGROUND_MAP_CODE]!!
        }.asLiveData()
    }
}
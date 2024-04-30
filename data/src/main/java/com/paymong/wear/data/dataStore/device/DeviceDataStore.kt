package com.paymong.wear.data.dataStore.device

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
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
        private val NETWORK_FLAG = booleanPreferencesKey("NETWORK_FLAG")
    }

    init {
        CoroutineScope(Dispatchers.IO).launch {
            context.device.edit { preferences ->
                preferences[CODE_INTEGRITY] = ""

                if (!preferences.contains(BUILD_VERSION)) {
                    preferences[BUILD_VERSION] = "1.0.0"
                }
                if (!preferences.contains(CODE_INTEGRITY)) {
                    preferences[CODE_INTEGRITY] = ""
                }
                if (!preferences.contains(DEVICE_ID)) {
                    preferences[DEVICE_ID] = UUID.randomUUID().toString()
                }
                if (!preferences.contains(NETWORK_FLAG)) {
                    preferences[NETWORK_FLAG] = true
                }
            }
        }
    }

    suspend fun findBuildVersion(): String {
        return context.device.data.map { preferences ->
            preferences[BUILD_VERSION]!!
        }.first()
    }

    suspend fun modifyBuildVersion(buildVersion: String) {
        context.device.edit { preferences ->
            preferences[BUILD_VERSION] = buildVersion
        }
    }

    suspend fun findCodeIntegrity(): String {
        return context.device.data.map { preferences ->
            preferences[CODE_INTEGRITY]!!
        }.first()
    }

    suspend fun modifyCodeIntegrity(codeIntegrity: String) {
        context.device.edit { preferences ->
            preferences[CODE_INTEGRITY] = codeIntegrity
        }
    }

    suspend fun findDeviceId(): String {
        return context.device.data.map { preferences ->
            preferences[DEVICE_ID]!!
        }.first()
    }

    suspend fun findNetworkFlag(): LiveData<Boolean> {
        return context.device.data.map { preferences ->
            preferences[NETWORK_FLAG]!!
        }.asLiveData()
    }

    suspend fun modifyNetworkFlag(networkFlag: Boolean) {
        context.device.edit { preferences ->
            preferences[NETWORK_FLAG] = networkFlag
        }
    }
}
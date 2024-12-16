package com.mongs.wear.data_.common.datastore

import android.content.Context
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
import java.util.UUID
import javax.inject.Inject

class AppDataStore @Inject constructor(
    private val context: Context
) {

    companion object {

        private const val APP_DATA_STORE_NAME = "APP"

        private val DEVICE_ID = stringPreferencesKey("DEVICE_ID")

        private val DEVICE_NAME = stringPreferencesKey("DEVICE_NAME")

        private val APP_CODE = stringPreferencesKey("APP_CODE")

        private val BUILD_VERSION = stringPreferencesKey("BUILD_VERSION")

        private val BG_MAP_TYPE_CODE = stringPreferencesKey("BG_MAP_TYPE_CODE")

        private val NETWORK = booleanPreferencesKey("NETWORK")
    }

    private val Context.store by preferencesDataStore(name = APP_DATA_STORE_NAME)

    init {
        CoroutineScope(Dispatchers.IO).launch {
            context.store.edit { preferences ->

                if (!preferences.contains(DEVICE_ID)) {
                    preferences[DEVICE_ID] = UUID.randomUUID().toString()
                }

                if (!preferences.contains(DEVICE_NAME)) {
                    preferences[DEVICE_NAME] = ""
                }

                if (!preferences.contains(APP_CODE)) {
                    preferences[APP_CODE] = "MONGS"
                }

                if (!preferences.contains(BUILD_VERSION)) {
                    preferences[BUILD_VERSION] = "1.0.0"
                }

                if (!preferences.contains(BG_MAP_TYPE_CODE)) {
                    preferences[BG_MAP_TYPE_CODE] = "MP000"
                }

                if (!preferences.contains(NETWORK)) {
                    preferences[NETWORK] = false
                }
            }
        }
    }

    suspend fun setDeviceId(deviceId: String) {
        context.store.edit { preferences ->
            preferences[DEVICE_ID] = deviceId
        }
    }

    suspend fun getDeviceId() : String {
        return runBlocking {
            context.store.data.map { preferences ->
                preferences[DEVICE_ID]!!
            }.first()
        }
    }

    suspend fun setDeviceName(deviceName: String) {
        context.store.edit { preferences ->
            preferences[DEVICE_NAME] = deviceName
        }
    }

    suspend fun getDeviceName() : String {
        return runBlocking {
            context.store.data.map { preferences ->
                preferences[DEVICE_NAME]!!
            }.first()
        }
    }

    suspend fun getAppCode() : String {
        return runBlocking {
            context.store.data.map { preferences ->
                preferences[APP_CODE]!!
            }.first()
        }
    }

    suspend fun setBuildVersion(buildVersion: String) {
        context.store.edit { preferences ->
            preferences[BUILD_VERSION] = buildVersion
        }
    }

    suspend fun getBuildVersion() : String {
        return runBlocking {
            context.store.data.map { preferences ->
                preferences[BUILD_VERSION]!!
            }.first()
        }
    }

    suspend fun setBgMapTypeCode(mapTypeCode: String) {
        context.store.edit { preferences ->
            preferences[BG_MAP_TYPE_CODE] = mapTypeCode
        }
    }

    suspend fun getBgMapTypeCodeLive() : LiveData<String> {
        return context.store.data.map { preferences ->
            preferences[BG_MAP_TYPE_CODE]!!
        }.asLiveData()
    }

    suspend fun setNetwork(network: Boolean) {
        context.store.edit { preferences ->
            preferences[NETWORK] = network
        }
    }

    suspend fun getNetworkLive() : LiveData<Boolean> {
        return context.store.data.map { preferences ->
            preferences[NETWORK]!!
        }.asLiveData()
    }
}
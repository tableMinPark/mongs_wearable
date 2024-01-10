package com.paymong.wear.data.dataStore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.paymong.wear.domain.viewModel.DefaultValue
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.Objects
import javax.inject.Inject

class DataSource @Inject constructor(
    private val context: Context
) {
    companion object {
        private val MAP_CODE = stringPreferencesKey("MAP_CODE")
        private val MAX_SLOT = intPreferencesKey("MAX_SLOT")
        private val PAY_POINT = intPreferencesKey("PAY_POINT")
        private val CONFIGURE_SOUND = floatPreferencesKey("CONFIGURE_SOUND")

        private val NETWORK_FLAG = booleanPreferencesKey("NETWORK_FLAG")
    }
    private val Context.appInfo  by preferencesDataStore(name = "APP_INFO")
    private val Context.configure  by preferencesDataStore(name = "CONFIGURE")

    init {
        CoroutineScope(Dispatchers.Main).launch {
            setNetworkFlag(true)
        }
    }

    fun getMapCode(): LiveData<String> {
        return context.appInfo.data.map { preferences ->
            preferences[MAP_CODE]!!
        }.asLiveData()
    }
    suspend fun setMapCode(mapCode: String) {
        context.appInfo.edit { preferences ->
            preferences[MAP_CODE] = mapCode
        }
    }

    fun getMaxSlot(): LiveData<Int> {
        return context.appInfo.data.map { preferences ->
            preferences[MAX_SLOT]!!
        }.asLiveData()
    }
    suspend fun setMaxSlot(maxSlot: Int) {
        context.appInfo.edit { preferences ->
            preferences[MAX_SLOT] = maxSlot
        }
    }

    fun getPayPoint(): LiveData<Int> {
        return context.appInfo.data.map { preferences ->
            preferences[PAY_POINT]!!
        }.asLiveData()
    }
    suspend fun setPayPoint(payPoint: Int) {
        context.appInfo.edit { preferences ->
            preferences[PAY_POINT] = payPoint
        }
    }

    fun getSound(): LiveData<Float> {
        return context.configure.data.map { preferences ->
            preferences[CONFIGURE_SOUND]!!
        }.asLiveData()
    }
    suspend fun setSound() {
        context.configure.edit { preferences ->
            if(!preferences.contains(CONFIGURE_SOUND)) {
                preferences[CONFIGURE_SOUND] = DefaultValue.sound
            }
        }
    }
    suspend fun setSound(sound: Float) {
        context.configure.edit { preferences ->
            preferences[CONFIGURE_SOUND] = sound
        }
    }

    fun getNetworkFlag(): LiveData<Boolean> {
        return context.configure.data.map { preferences ->
            preferences[NETWORK_FLAG]!!
        }.asLiveData()
    }
    fun setNetworkFlag(flag: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            context.configure.edit { preferences ->
                preferences[NETWORK_FLAG] = flag
            }
        }
    }
}
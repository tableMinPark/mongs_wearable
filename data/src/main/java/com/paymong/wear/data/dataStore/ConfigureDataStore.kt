package com.paymong.wear.data.dataStore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.paymong.wear.domain.DefaultValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class ConfigureDataStore @Inject constructor(
    private val context: Context
)  {
    private val Context.configure  by preferencesDataStore(name = "CONFIGURE")

    companion object {
        private val SOUND = floatPreferencesKey("SOUND")
        private val BACKGROUND_MAP_CODE = stringPreferencesKey("BACKGROUND_MAP_CODE")
    }

    init {
        CoroutineScope(Dispatchers.IO).launch {
            context.configure.edit { preferences ->
                if (!preferences.contains(SOUND)) {
                    preferences[SOUND] = DefaultValue.SOUND
                }
                if (!preferences.contains(BACKGROUND_MAP_CODE)) {
                    preferences[BACKGROUND_MAP_CODE] = DefaultValue.BACKGROUND_MAP_CODE
                }
            }
        }
    }

    fun findSound(): LiveData<Float> {
        return context.configure.data.map { preferences ->
            preferences[SOUND]!!
        }.asLiveData()
    }
    suspend fun modifySound(sound: Float) {
        context.configure.edit { preferences ->
            preferences[SOUND] = sound
        }
    }

    fun findBackgroundMapCode(): LiveData<String> {
        return context.configure.data.map { preferences ->
            preferences[BACKGROUND_MAP_CODE]!!
        }.asLiveData()
    }
    suspend fun modifyBackgroundMapCode(backgroundMapCode: String) {
        context.configure.edit { preferences ->
            preferences[BACKGROUND_MAP_CODE] = backgroundMapCode
        }
    }
}
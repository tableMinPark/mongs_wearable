package com.paymong.wear.data.dataStore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
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
import javax.inject.Inject

class MemberDataStore @Inject constructor(
    private val context: Context
)  {
    private val Context.member  by preferencesDataStore(name = "MEMBER")

    companion object {
        private val ACCESS_TOKEN = stringPreferencesKey("ACCESS_TOKEN")
        private val REFRESH_TOKEN = stringPreferencesKey("REFRESH_TOKEN")
        private val STAR_POINT = intPreferencesKey("STAR_POINT")
        private val MAX_SLOT = intPreferencesKey("MAX_SLOT")
    }

    init {
        CoroutineScope(Dispatchers.IO).launch {
            context.member.edit { preferences ->
                if (!preferences.contains(ACCESS_TOKEN)) {
                    preferences[ACCESS_TOKEN] = ""
                }
                if (!preferences.contains(REFRESH_TOKEN)) {
                    preferences[REFRESH_TOKEN] = ""
                }
                if (!preferences.contains(STAR_POINT)) {
                    preferences[STAR_POINT] = 0
                }
                if (!preferences.contains(MAX_SLOT)) {
                    preferences[MAX_SLOT] = 1
                }
            }
        }
    }

    suspend fun setAccessToken(accessToken: String) {
        context.member.edit { preferences ->
            preferences[ACCESS_TOKEN] = accessToken
        }
    }
    fun getAccessToken(): String {
        return runBlocking {
            context.member.data.map {preferences ->
                preferences[ACCESS_TOKEN]!!
            }.first()
        }
    }

    suspend fun setRefreshToken(refreshToken: String) {
        context.member.edit { preferences ->
            preferences[REFRESH_TOKEN] = refreshToken
        }
    }
    fun getRefreshToken(): String {
        return runBlocking {
            context.member.data.map {preferences ->
                preferences[REFRESH_TOKEN]!!
            }.first()
        }
    }

    suspend fun setStarPoint(starPoint: Int) {
        context.member.edit { preferences ->
            preferences[STAR_POINT] = starPoint
        }
    }
    fun getStarPointLive(): LiveData<Int> {
        return context.member.data.map { preferences ->
            preferences[STAR_POINT]!!
        }.asLiveData()
    }

    suspend fun setMaxSlot(maxSlot: Int) {
        context.member.edit { preferences ->
            preferences[MAX_SLOT] = maxSlot
        }
    }
    suspend fun getMaxSlot(): Int {
        return runBlocking {
            context.member.data.map { preferences ->
                preferences[MAX_SLOT]!!
            }.first()
        }
    }
}
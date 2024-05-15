package com.paymong.wear.data.dataStore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class TokenDataStore @Inject constructor(
    private val context: Context
)  {
    private val Context.token  by preferencesDataStore(name = "TOKEN")

    companion object {
        private val ACCESS_TOKEN = stringPreferencesKey("ACCESS_TOKEN")
        private val REFRESH_TOKEN = stringPreferencesKey("REFRESH_TOKEN")
    }

    init {
        CoroutineScope(Dispatchers.IO).launch {
            context.token.edit { preferences ->
                if (!preferences.contains(ACCESS_TOKEN)) {
                    preferences[ACCESS_TOKEN] = ""
                }
                if (!preferences.contains(REFRESH_TOKEN)) {
                    preferences[REFRESH_TOKEN] = ""
                }
            }
        }
    }

    fun findAccessToken(): String {
        return runBlocking {
            context.token.data.map {preferences ->
                preferences[ACCESS_TOKEN]!!
            }.first()
        }
    }
    suspend fun modifyAccessToken(accessToken: String) {
        context.token.edit { preferences ->
            preferences[ACCESS_TOKEN] = accessToken
        }
    }

    fun findRefreshToken(): String {
        return runBlocking {
            context.token.data.map {preferences ->
                preferences[REFRESH_TOKEN]!!
            }.first()
        }
    }
    suspend fun modifyRefreshToken(refreshToken: String) {
        context.token.edit { preferences ->
            preferences[REFRESH_TOKEN] = refreshToken
        }
    }
}
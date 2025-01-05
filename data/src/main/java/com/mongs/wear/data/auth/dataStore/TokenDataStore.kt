package com.mongs.wear.data.auth.dataStore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {

        private const val TOKEN_DATA_STORE_NAME = "TOKEN"

        private val ACCESS_TOKEN = stringPreferencesKey("ACCESS_TOKEN")

        private val REFRESH_TOKEN = stringPreferencesKey("REFRESH_TOKEN")
    }

    private val Context.store by preferencesDataStore(name = TOKEN_DATA_STORE_NAME)

    init {
        CoroutineScope(Dispatchers.IO).launch {
            context.store.edit { preferences ->

                if (!preferences.contains(ACCESS_TOKEN)) {
                    preferences[ACCESS_TOKEN] = ""
                }

                if (!preferences.contains(REFRESH_TOKEN)) {
                    preferences[REFRESH_TOKEN] = ""
                }
            }
        }
    }

    /**
     * access Token 등록
     */
    suspend fun setAccessToken(accessToken: String) {
        context.store.edit { preferences ->
            preferences[ACCESS_TOKEN] = accessToken
        }
    }

    /**
     * access Token 조회
     */
    fun getAccessToken() : String {
        return runBlocking {
            context.store.data.map { preferences ->
                preferences[ACCESS_TOKEN]!!
            }.first()
        }
    }

    /**
     * refresh Token 등록
     */
    suspend fun setRefreshToken(refreshToken: String) {
        context.store.edit { preferences ->
            preferences[REFRESH_TOKEN] = refreshToken
        }
    }

    /**
     * refresh Token 조회
     */
    fun getRefreshToken() : String {
        return runBlocking {
            context.store.data.map { preferences ->
                preferences[REFRESH_TOKEN]!!
            }.first()
        }
    }
}
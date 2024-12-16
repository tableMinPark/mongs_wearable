package com.mongs.wear.data_.user.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class PlayerDataStore @Inject constructor(
    private val context: Context
) {

    companion object {

        private const val MEMBER_DATA_STORE_NAME = "MEMBER"

        private val STAR_POINT = intPreferencesKey("STAR_POINT")

        private val MAX_SLOT = intPreferencesKey("MAX_SLOT")

        private val WALKING_COUNT = intPreferencesKey("WALKING_COUNT")
    }

    private val Context.store  by preferencesDataStore(name = MEMBER_DATA_STORE_NAME)

    init {
        CoroutineScope(Dispatchers.IO).launch {
            context.store.edit { preferences ->

                if (!preferences.contains(STAR_POINT)) {
                    preferences[STAR_POINT] = 0
                }

                if (!preferences.contains(MAX_SLOT)) {
                    preferences[MAX_SLOT] = 0
                }

                if (!preferences.contains(WALKING_COUNT)) {
                    preferences[WALKING_COUNT] = 0
                }
            }
        }
    }

    suspend fun setStarPoint(starPoint: Int) {
        context.store.edit { preferences ->
            preferences[STAR_POINT] = starPoint
        }
    }

    suspend fun getStarPoint() : Int {
        return runBlocking {
            context.store.data.map { preferences ->
                preferences[STAR_POINT]!!
            }.first()
        }
    }

    suspend fun setMaxSlot(maxSlot: Int) {
        context.store.edit { preferences ->
            preferences[MAX_SLOT] = maxSlot
        }
    }

    suspend fun getMaxSlot() : Int {
        return runBlocking {
            context.store.data.map { preferences ->
                preferences[MAX_SLOT]!!
            }.first()
        }
    }

    suspend fun setWalkingCount(walkingCount: Int) {
        context.store.edit { preferences ->
            preferences[WALKING_COUNT] = walkingCount
        }
    }

    suspend fun getWalkingCount() : Int {
        return runBlocking {
            context.store.data.map { preferences ->
                preferences[WALKING_COUNT]!!
            }.first()
        }
    }
}
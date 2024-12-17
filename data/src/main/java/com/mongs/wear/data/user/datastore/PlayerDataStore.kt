package com.mongs.wear.data.user.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlayerDataStore @Inject constructor(
    private val context: Context
) {

    companion object {

        private const val MEMBER_DATA_STORE_NAME = "MEMBER"

        private val STAR_POINT = intPreferencesKey("STAR_POINT")

        private val SLOT_COUNT = intPreferencesKey("SLOT_COUNT")

        private val WALKING_COUNT = intPreferencesKey("WALKING_COUNT")
    }

    private val Context.store by preferencesDataStore(name = MEMBER_DATA_STORE_NAME)

    init {
        CoroutineScope(Dispatchers.IO).launch {
            context.store.edit { preferences ->

                preferences[STAR_POINT] = 0

                preferences[SLOT_COUNT] = 0

                preferences[WALKING_COUNT] = 0
            }
        }
    }

    suspend fun setStarPoint(starPoint: Int) {
        context.store.edit { preferences ->
            preferences[STAR_POINT] = starPoint
        }
    }

    suspend fun getStarPointLive(): LiveData<Int> {
        return context.store.data.map { preferences ->
            preferences[STAR_POINT]!!
        }.asLiveData()
    }


    suspend fun setSlotCount(maxSlot: Int) {
        context.store.edit { preferences ->
            preferences[SLOT_COUNT] = maxSlot
        }
    }

    suspend fun getSlotCountLive() : LiveData<Int> {
        return context.store.data.map { preferences ->
            preferences[SLOT_COUNT]!!
        }.asLiveData()
    }

    suspend fun setWalkingCount(walkingCount: Int) {
        context.store.edit { preferences ->
            preferences[WALKING_COUNT] = walkingCount
        }
    }

    suspend fun getWalkingCountLive() : LiveData<Int> {
        return context.store.data.map { preferences ->
            preferences[WALKING_COUNT]!!
        }.asLiveData()

    }
}
package com.mongs.wear.data.user.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
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
class PlayerDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        private const val MEMBER_DATA_STORE_NAME = "MEMBER"

        private val STAR_POINT = intPreferencesKey("STAR_POINT")
        private val STEPS = intPreferencesKey("STEPS")
        private val WALKING_COUNT = intPreferencesKey("WALKING_COUNT")
        private val CONSUME_WALKING_COUNT = intPreferencesKey("CONSUME_WALKING_COUNT")
        private val TOTAL_WALKING_COUNT = intPreferencesKey("TOTAL_WALKING_COUNT")
    }

    private val Context.store by preferencesDataStore(name = MEMBER_DATA_STORE_NAME)

    init {
        CoroutineScope(Dispatchers.IO).launch {
            context.store.edit { preferences ->

                preferences[STAR_POINT] = 0

                if (!preferences.contains(STEPS)) {
                    preferences[STEPS] = 0
                }

                if (!preferences.contains(WALKING_COUNT)) {
                    preferences[WALKING_COUNT] = 0
                }

                if (!preferences.contains(CONSUME_WALKING_COUNT)) {
                    preferences[CONSUME_WALKING_COUNT] = 0
                }

                if (!preferences.contains(TOTAL_WALKING_COUNT)) {
                    preferences[TOTAL_WALKING_COUNT] = 0
                }
            }
        }
    }

    suspend fun setStarPoint(starPoint: Int) {
        context.store.edit { preferences ->
            preferences[STAR_POINT] = starPoint
        }
    }

    fun getStarPointLive(): LiveData<Int> {
        return context.store.data.map { preferences ->
            preferences[STAR_POINT]!!
        }.asLiveData()
    }

    fun getSteps(): Int {
        return runBlocking {
            context.store.data.map { preferences ->
                preferences[STEPS]!!
            }.first()
        }
    }

    fun getStepsLive() : LiveData<Int> {
        return context.store.data.map { preferences ->
            preferences[STEPS]!!
        }.asLiveData()
    }

    suspend fun setWalkingCount(walkingCount: Int) {
        context.store.edit { preferences ->
            preferences[WALKING_COUNT] = walkingCount

            val totalWalkingCount = preferences[TOTAL_WALKING_COUNT]!!
            val consumeWalkingCount = preferences[CONSUME_WALKING_COUNT]!!

            preferences[STEPS] = walkingCount + (totalWalkingCount - consumeWalkingCount)
        }
    }

    suspend fun setConsumeWalkingCount(consumeWalkingCount: Int) {
        context.store.edit { preferences ->
            preferences[CONSUME_WALKING_COUNT] = consumeWalkingCount

            val totalWalkingCount = preferences[TOTAL_WALKING_COUNT]!!
            val walkingCount = preferences[WALKING_COUNT]!!

            preferences[STEPS] = walkingCount + (totalWalkingCount - consumeWalkingCount)
        }
    }

    fun getConsumeWalkingCount(): Int {
        return runBlocking {
            context.store.data.map { preferences ->
                preferences[CONSUME_WALKING_COUNT]!!
            }.first()
        }
    }

    suspend fun setTotalWalkingCount(totalWalkingCount: Int) {
        context.store.edit { preferences ->
            preferences[TOTAL_WALKING_COUNT] = totalWalkingCount

            val consumeWalkingCount = preferences[CONSUME_WALKING_COUNT]!!
            val walkingCount = preferences[WALKING_COUNT]!!

            preferences[STEPS] = walkingCount + (totalWalkingCount - consumeWalkingCount)
        }
    }

    suspend fun getTotalWalkingCount() : Int {
        return runBlocking {
            context.store.data.map { preferences ->
                preferences[TOTAL_WALKING_COUNT]!!
            }.first()
        }
    }
}
package com.paymong.wear.data.dataStore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.paymong.wear.domain.DefaultValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class MemberDataStore @Inject constructor(
    private val context: Context
)  {
    private val Context.member  by preferencesDataStore(name = "MEMBER")

    companion object {
        private val STAR_POINT = intPreferencesKey("STAR_POINT")
        private val MAX_SLOT = intPreferencesKey("MAX_SLOT")
    }

    init {
        CoroutineScope(Dispatchers.IO).launch {
            context.member.edit { preferences ->
                if (!preferences.contains(STAR_POINT)) {
                    preferences[STAR_POINT] = DefaultValue.STAR_POINT
                }
                if (!preferences.contains(MAX_SLOT)) {
                    preferences[MAX_SLOT] = DefaultValue.MAX_SLOT
                }
            }
        }
    }

    fun findStarPoint(): LiveData<Int> {
        return context.member.data.map { preferences ->
            preferences[STAR_POINT]!!
        }.asLiveData()
    }
    suspend fun modifyStarPoint(starPoint: Int) {
        context.member.edit { preferences ->
            preferences[STAR_POINT] = starPoint
        }
    }
    suspend fun findMaxSlot(): Int {
        return context.member.data.map { preferences ->
            preferences[MAX_SLOT]!!
        }.first()
    }
    suspend fun modifyMaxSlot(maxSlot: Int) {
        context.member.edit { preferences ->
            preferences[MAX_SLOT] = maxSlot
        }
    }
}
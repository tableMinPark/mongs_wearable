package com.mongs.wear.data.dataStore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.mongs.wear.domain.vo.BattleRoomVo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class BattleDataStore @Inject constructor(
    private val context: Context
) {
    private val Context.battle  by preferencesDataStore(name = "BATTLE")

    companion object {
        private val ROOM_ID = stringPreferencesKey("ROOM_ID")
        private val IS_MATCHED = booleanPreferencesKey("IS_MATCHED")
    }

    init {
        CoroutineScope(Dispatchers.IO).launch {
            context.battle.edit { preferences ->
                preferences[ROOM_ID] = ""
                preferences[IS_MATCHED] = false
            }
        }
    }

    suspend fun setRoomId(roomId: String) {
        context.battle.edit { preferences ->
            preferences[ROOM_ID] = roomId
        }
    }
    suspend fun getRoomId(): String {
        return runBlocking {
            context.battle.data.map { preferences ->
                preferences[ROOM_ID]!!
            }.first()
        }
    }

    suspend fun setIsMatched(isMatched: Boolean) {
        context.battle.edit { preferences ->
            preferences[IS_MATCHED] = isMatched
        }
    }

    suspend fun getBattleRoomLive(): LiveData<BattleRoomVo> {
        return context.battle.data.map { preferences ->
            BattleRoomVo(
                roomId = preferences[ROOM_ID]!!,
                isMatched = preferences[IS_MATCHED]!!,
            )
        }.asLiveData()
    }
}
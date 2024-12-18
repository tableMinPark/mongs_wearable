package com.mongs.wear.domain.battle.repository

import androidx.lifecycle.LiveData
import com.mongs.wear.core.enums.MatchRoundCode
import com.mongs.wear.domain.battle.model.MatchModel
import com.mongs.wear.domain.battle.model.MatchPlayerModel

interface BattleRepository {

    suspend fun getMatchLive(): LiveData<MatchModel>

    suspend fun createMatching(mongId: Long)

    suspend fun deleteMatching(mongId: Long)

    suspend fun updateOverMatch(roomId: Long)

    suspend fun enterMatch(roomId: Long)

    suspend fun pickMatch(roomId: Long, targetPlayerId: String, pickCode: MatchRoundCode)

    suspend fun exitMatch(roomId: Long)

    suspend fun getMyMatchPlayerLive(): LiveData<MatchPlayerModel>

    suspend fun getRiverMatchPlayerLive(): LiveData<MatchPlayerModel>
}
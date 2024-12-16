package com.mongs.wear.domain.repositroy

import androidx.lifecycle.LiveData

interface PlayerRepository {

    suspend fun setMember()

    suspend fun buySlot(): Int

    suspend fun getStarPointLive(): LiveData<Int>

    suspend fun setMaxSlot(maxSlot: Int)

    suspend fun getMaxSlotLive(): LiveData<Int>

    suspend fun setStartStepCount(stepCount: Int)

    suspend fun getStartStepCount(): Int

    suspend fun setEndStepCount(stepCount: Int)

    suspend fun getEndStepCount(): Int

    suspend fun setWalkingCount(walkingCount: Int)

    suspend fun getWalkingCount(): Int

    suspend fun getWalkingCountLive(): LiveData<Int>

    suspend fun exchangePayPointWalking(mongId: Long, walkingCount: Int)
}
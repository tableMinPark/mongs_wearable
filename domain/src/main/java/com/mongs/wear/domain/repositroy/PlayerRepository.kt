package com.mongs.wear.domain.repositroy

import androidx.lifecycle.LiveData

interface PlayerRepository {

    suspend fun updatePlayer()

    suspend fun buySlot()

    suspend fun getStarPointLive(): LiveData<Int>

    suspend fun getSlotCountLive(): LiveData<Int>

    suspend fun setWalkingCount(walkingCount: Int)

    suspend fun getWalkingCountLive(): LiveData<Int>

    suspend fun exchangeWalkingCount(mongId: Long, walkingCount: Int)
}
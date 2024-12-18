package com.mongs.wear.domain.player.repository

import androidx.lifecycle.LiveData

interface PlayerRepository {

    suspend fun updatePlayer()

    suspend fun buySlot()

    suspend fun getStarPointLive(): LiveData<Int>

    suspend fun getSlotCountLive(): LiveData<Int>

    suspend fun setWalkingCount(walkingCount: Int)

    suspend fun getWalkingCountLive(): LiveData<Int>

    suspend fun chargeStarPoint(receipt: String, starPoint: Int)

    suspend fun exchangeStarPoint(mongId: Long, starPoint: Int)

    suspend fun chargeWalking(walkingCount: Int)

    suspend fun exchangeWalking(mongId: Long, walkingCount: Int)
}
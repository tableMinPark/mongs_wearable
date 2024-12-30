package com.mongs.wear.domain.player.repository

import androidx.lifecycle.LiveData
import java.time.LocalDateTime

interface PlayerRepository {

    suspend fun updatePlayer()

    suspend fun buySlot()

    suspend fun getStarPointLive(): LiveData<Int>

    suspend fun getSlotCountLive(): LiveData<Int>

    suspend fun chargeStarPoint(receipt: String, starPoint: Int)

    suspend fun exchangeStarPoint(mongId: Long, starPoint: Int)

    suspend fun getStepsLive(): LiveData<Int>

    suspend fun syncWalkingCount(deviceId: String, totalWalkingCount: Int, deviceBootedDt: LocalDateTime)

    suspend fun setWalkingCount(totalWalkingCount: Int)

    suspend fun exchangeWalking(mongId: Long, walkingCount: Int, deviceBootedDt: LocalDateTime)
}
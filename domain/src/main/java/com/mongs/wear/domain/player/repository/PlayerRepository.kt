package com.mongs.wear.domain.player.repository

import androidx.lifecycle.LiveData
import java.time.LocalDateTime

interface PlayerRepository {

    suspend fun updatePlayer()

    suspend fun getStarPointLive(): LiveData<Int>

    suspend fun getSlotCount(): Int

    suspend fun buySlot()

    suspend fun exchangeStarPoint(mongId: Long, starPoint: Int)

    suspend fun exchangeWalkingCount(mongId: Long, walkingCount: Int, deviceBootedDt: LocalDateTime)

    suspend fun syncTotalWalkingCount(deviceId: String, totalWalkingCount: Int, deviceBootedDt: LocalDateTime)

    suspend fun getStepsLive(): LiveData<Int>

    suspend fun setTotalWalkingCount(totalWalkingCount: Int)
}
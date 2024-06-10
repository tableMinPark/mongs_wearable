package com.mongs.wear.domain.repositroy

import androidx.lifecycle.LiveData

interface MemberRepository {
    suspend fun setMember()
    suspend fun buySlot(): Int
    suspend fun setAccessToken(accessToken: String)
    suspend fun getAccessToken(): String
    suspend fun setRefreshToken(refreshToken: String)
    suspend fun getRefreshToken(): String
    suspend fun setStarPoint(starPoint: Int)
    suspend fun getStarPointLive(): LiveData<Int>
    suspend fun setMaxSlot(maxSlot: Int)
    suspend fun getMaxSlotLive(): LiveData<Int>
    suspend fun setWalkingCount(walkingCount: Int)
    suspend fun getWalkingCount(): Int
    suspend fun getWalkingCountLive(): LiveData<Int>
    suspend fun addWalkingCount(addWalkingCount: Int)
}
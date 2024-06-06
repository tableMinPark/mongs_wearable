package com.mongs.wear.domain.repositroy

import androidx.lifecycle.LiveData

interface MemberRepository {
    suspend fun setMember(accountId: Long)
    suspend fun buySlot(): Int
    suspend fun setAccessToken(accessToken: String)
    suspend fun getAccessToken(): String
    suspend fun setRefreshToken(refreshToken: String)
    suspend fun getRefreshToken(): String
    suspend fun setStarPoint(starPoint: Int)
    suspend fun getStarPointLive(): LiveData<Int>
    suspend fun setMaxSlot(maxSlot: Int)
    suspend fun getMaxSlotLive(): LiveData<Int>
}
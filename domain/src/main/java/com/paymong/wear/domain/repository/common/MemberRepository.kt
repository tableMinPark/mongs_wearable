package com.paymong.wear.domain.repository.common

import androidx.lifecycle.LiveData

interface MemberRepository {
    suspend fun initializeMember()
    suspend fun setStarPoint(starPoint: Int)
    suspend fun getStarPoint(): LiveData<Int>
    suspend fun setMaxSlot(maxSlot: Int)
    suspend fun getMaxSlot(): Int
}
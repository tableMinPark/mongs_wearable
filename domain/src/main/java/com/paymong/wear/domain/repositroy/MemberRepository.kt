package com.paymong.wear.domain.repositroy

import androidx.lifecycle.LiveData

interface MemberRepository {
    suspend fun setMember(accountId: Long)
    suspend fun buySlot(): Int

    suspend fun setMaxSlot(maxSlot: Int)
    suspend fun getMaxSlotLive(): LiveData<Int>

    suspend fun getStarPointLive(): LiveData<Int>
}
package com.paymong.wear.domain.repositroy

import androidx.lifecycle.LiveData
import com.paymong.wear.domain.model.SlotModel

interface SlotRepository {
    suspend fun setNowSlot(mongId: Long)
    suspend fun getNowSlot(): SlotModel
    suspend fun getNowSlotLive(): LiveData<SlotModel>

    suspend fun setSlots(accountId: Long)
    suspend fun getSlots(): List<SlotModel>
}
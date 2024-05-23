package com.paymong.wear.domain.repositroy

import androidx.lifecycle.LiveData
import com.paymong.wear.domain.model.SlotModel
import com.paymong.wear.domain.vo.SlotVo

interface SlotRepository {
    suspend fun setSlots(accountId: Long)
    suspend fun getSlotsLive(): LiveData<List<SlotVo>>
    suspend fun setNowSlot(mongId: Long)
    suspend fun getNowSlot(): SlotModel
    suspend fun getNowSlotLive(): LiveData<SlotModel>
}
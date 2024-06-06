package com.mongs.wear.domain.repositroy

import androidx.lifecycle.LiveData
import com.mongs.wear.domain.model.SlotModel
import com.mongs.wear.domain.vo.SlotVo

interface SlotRepository {
    suspend fun setSlots(accountId: Long)
    suspend fun getSlotsLive(): LiveData<List<SlotVo>>
    suspend fun setNowSlot(mongId: Long)
    suspend fun getNowSlot(): SlotModel
    suspend fun getNowSlotLive(): LiveData<SlotModel>
}
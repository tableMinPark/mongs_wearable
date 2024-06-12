package com.mongs.wear.domain.repositroy

import androidx.lifecycle.LiveData
import com.mongs.wear.domain.model.SlotModel
import com.mongs.wear.domain.vo.SlotVo

interface SlotRepository {
    suspend fun syncNowSlot()
    suspend fun setSlots()
    suspend fun getSlots(): LiveData<List<SlotVo>>
    suspend fun setNowSlot(mongId: Long)
    suspend fun getNowSlot(): SlotModel
    suspend fun getNowSlotLive(): LiveData<SlotModel>
}
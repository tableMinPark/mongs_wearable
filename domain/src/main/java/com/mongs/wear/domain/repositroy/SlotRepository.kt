package com.mongs.wear.domain.repositroy

import androidx.lifecycle.LiveData
import com.mongs.wear.domain.model.SlotModel
import com.mongs.wear.domain.vo.SlotVo

interface SlotRepository {

    suspend fun syncNowSlot()

    suspend fun setSlots(subScribeMong: suspend (Long) -> Unit)

    suspend fun getSlots(subScribeMong: suspend (Long) -> Unit): LiveData<List<SlotVo>>

    suspend fun getSlot(mongId: Long): SlotModel

    suspend fun setNowSlot(mongId: Long)

    suspend fun getNowSlot(): SlotModel

    suspend fun getNowSlotLive(): LiveData<SlotModel?>
}
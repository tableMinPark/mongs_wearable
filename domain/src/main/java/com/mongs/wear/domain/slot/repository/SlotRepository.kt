package com.mongs.wear.domain.slot.repository

import androidx.lifecycle.LiveData
import com.mongs.wear.domain.slot.model.SlotModel

interface SlotRepository {

    suspend fun getCurrentSlot(): SlotModel?

    suspend fun getCurrentSlotLive(): LiveData<SlotModel?>

    suspend fun getSlotsLive(): LiveData<List<SlotModel>>

    suspend fun setCurrentSlot(mongId: Long)
}
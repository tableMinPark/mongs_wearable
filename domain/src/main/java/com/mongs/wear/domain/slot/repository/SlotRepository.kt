package com.mongs.wear.domain.slot.repository

import androidx.lifecycle.LiveData
import com.mongs.wear.domain.slot.model.SlotModel

interface SlotRepository {

    suspend fun updateCurrentSlot()

    suspend fun getSlotLive(mongId: Long): LiveData<SlotModel?>

    suspend fun getSlotsLive(): LiveData<List<SlotModel>>

    suspend fun setSlot(mongId: Long)
}
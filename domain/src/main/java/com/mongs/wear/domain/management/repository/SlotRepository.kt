package com.mongs.wear.domain.management.repository

import androidx.lifecycle.LiveData
import com.mongs.wear.domain.management.model.MongModel

interface SlotRepository {

    suspend fun setCurrentSlot(mongId: Long)

    suspend fun getCurrentSlot(): MongModel?

    suspend fun getCurrentSlotLive(): LiveData<MongModel?>

    suspend fun updateCurrentSlot()
}
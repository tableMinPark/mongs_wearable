package com.paymong.wear.domain.repository

import androidx.lifecycle.LiveData
import com.paymong.wear.domain.model.MongModel

interface MongRepository {
    suspend fun getMong(): LiveData<MongModel>
    suspend fun getAllMong(): LiveData<List<MongModel>>
    suspend fun initSetMong(callback: () -> Unit)
    suspend fun generateMong(callback: () -> Unit)
    suspend fun removeMong(callback: () -> Unit, slotId: Long)
    suspend fun getSlotId(): Long
    suspend fun setSlotId(callback: () -> Unit, slotId: Long)
    suspend fun getMongState(): String
    suspend fun setMongState(stateCode: String)
    suspend fun setPoopCount(poopCount: Int)
    suspend fun setMongSleep()
    suspend fun setMongWakeUp()
}
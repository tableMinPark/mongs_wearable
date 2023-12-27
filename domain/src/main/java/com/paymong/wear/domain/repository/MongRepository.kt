package com.paymong.wear.domain.repository

import androidx.lifecycle.LiveData
import com.paymong.wear.domain.model.MongModel

interface MongRepository {
    suspend fun initSetMong()
    suspend fun generateMong()
    suspend fun setSlot(slotId: Long)
    fun getMong(): LiveData<LiveData<MongModel>>
    fun getAllMong(): LiveData<List<MongModel>>
    suspend fun setMongState(stateCode: String)
    suspend fun findMongState(): String
    suspend fun setPoopCount(poopCount: Int)
    suspend fun setMongSleep()
    suspend fun setMongWakeUp()
}
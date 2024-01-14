package com.paymong.wear.domain.repository

import androidx.lifecycle.LiveData
import com.paymong.wear.domain.model.SlotModel

interface SlotRepository {
    // Fun
    fun initSlotInfo()
    suspend fun getSlot(): LiveData<SlotModel>
    suspend fun getAllSlot(): LiveData<List<SlotModel>>
    suspend fun setSlot(slotId: Long)
    suspend fun generateSlot()
    suspend fun removeSlot(slotId: Long)
    suspend fun setSlotNextStateToState(stateCode: String)
    suspend fun setSlotStateToNextState()
    suspend fun setSlotMongCodeToNextMongCode()
    // Colum
    suspend fun getSlotMongState(): String
    suspend fun setSlotMongState(stateCode: String)
    suspend fun setSlotMongPoopCount(poopCount: Int)
    suspend fun setSlotMongSleep()
    suspend fun setSlotMongWakeUp()

}
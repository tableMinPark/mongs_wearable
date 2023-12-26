package com.paymong.wear.domain.repository

import androidx.lifecycle.LiveData
import com.paymong.wear.domain.model.MongModel

interface MongRepository {
    suspend fun initSetMong()
    suspend fun generateMong()
    suspend fun setSlot(slotId: Long)
    fun getMong(): LiveData<LiveData<MongModel>>
    suspend fun setMongState(stateCode: String)
    suspend fun findMongState(): String
    suspend fun setPoopCount(poopCount: Int)
    suspend fun setMongSleep()
    suspend fun setMongWakeUp()


//    fun initMong(callback: () -> Unit)
//    fun setNowMong(slotId: Int)
//    fun setMongCondition(mongCondition: MongCondition)
//    fun getMongCondition(): LiveData<MongCondition>
//    fun setMongInfo(mongInfo: MongInfo)
//    fun getMongInfo(): LiveData<MongInfo>
//    fun setMongState(mongState: MongState)
//    fun getMongState(): LiveData<MongState>
}
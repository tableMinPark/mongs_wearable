package com.paymong.wear.domain.repository

import androidx.lifecycle.LiveData
import com.paymong.wear.domain.dto.room.MongCondition
import com.paymong.wear.domain.dto.room.MongInfo
import com.paymong.wear.domain.dto.room.MongState

interface MongRepository {
    fun initMong(callback: () -> Unit)
    fun setNowMong(slotId: Int)
    fun setMongCondition(mongCondition: MongCondition)
    fun getMongCondition(): LiveData<MongCondition>
    fun setMongInfo(mongInfo: MongInfo)
    fun getMongInfo(): LiveData<MongInfo>
    fun setMongState(mongState: MongState)
    fun getMongState(): LiveData<MongState>
}
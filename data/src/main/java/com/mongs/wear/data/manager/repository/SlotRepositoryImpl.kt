package com.mongs.wear.data.manager.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mongs.wear.domain.code.ShiftCode
import com.mongs.wear.domain.code.StateCode
import com.mongs.wear.domain.model.SlotModel
import com.mongs.wear.domain.repositroy.SlotRepository
import com.mongs.wear.domain.vo.SlotVo
import java.time.LocalDateTime
import javax.inject.Inject

class SlotRepositoryImpl @Inject constructor(
): SlotRepository {

    override suspend fun syncNowSlot() {
        Log.d("SlotRepositoryImpl", "syncNowSlot")
    }

    override suspend fun setSlots(subScribeMong: suspend (Long) -> Unit) {
        Log.d("SlotRepositoryImpl", "setSlots")
    }

    override suspend fun getSlots(subScribeMong: suspend (Long) -> Unit): LiveData<List<SlotVo>> {
        Log.d("SlotRepositoryImpl", "getSlots")
        return MutableLiveData(ArrayList())
    }

    override suspend fun getSlot(mongId: Long): SlotModel {
        Log.d("SlotRepositoryImpl", "getSlot")
        return SlotModel(
            0L,
            "",
            "",
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0,
            false,
            0.0,
            StateCode.EMPTY,
            ShiftCode.EMPTY,
            0,
            LocalDateTime.now(),
            false,
            false,
            false,
            false,
            false,
        )
    }

    override suspend fun setNowSlot(mongId: Long) {
        Log.d("SlotRepositoryImpl", "setNowSlot")
    }

    override suspend fun getNowSlot(): SlotModel {
        Log.d("SlotRepositoryImpl", "getNowSlot")
        return SlotModel(
            0L,
            "",
            "",
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0,
            false,
            0.0,
            StateCode.EMPTY,
            ShiftCode.EMPTY,
            0,
            LocalDateTime.now(),
            false,
            false,
            false,
            false,
            false,
        )
    }

    override suspend fun getNowSlotLive(): LiveData<SlotModel?> {
        Log.d("SlotRepositoryImpl", "getNowSlotLive")
        return MutableLiveData(null)
    }
}
package com.paymong.wear.domain.repository.slot

import androidx.lifecycle.LiveData
import com.paymong.wear.domain.code.ShiftCode
import com.paymong.wear.domain.code.StateCode
import com.paymong.wear.domain.refac.repository.common.vo.FeedHistoryVo
import com.paymong.wear.domain.refac.vo.SlotVo

interface SlotRepository {
    suspend fun initializeSlot()
    suspend fun setNowSlot()
    suspend fun setNowSlot(mongId: Long)
    suspend fun getNowSlot(): LiveData<SlotVo>
    suspend fun getAllSlot(): LiveData<List<SlotVo>>
    suspend fun addSlot(name: String, sleepStart: String, sleepEnd: String): SlotVo
    suspend fun removeSlot(mongId: Long)

    suspend fun setNowSlotStateCode(stateCode: StateCode)
    suspend fun getNowSlotStateCode(): String
    suspend fun setNowSlotShiftCode(shiftCode: ShiftCode)
    suspend fun getNowSlotShiftCode(): String
    suspend fun setNowSlotIsHappy(delay: Long)
    suspend fun setNowSlotIsEating(delay: Long)

    suspend fun strokeNowSlot()
    suspend fun feedNowSlot(foodCode: String)
    suspend fun sleepingNowSlot()
    suspend fun poopCleanNowSlot()
    suspend fun evolutionNowSlot()
    suspend fun graduationCheckNowSlot()
    suspend fun graduationSlot(mongId: Long)

    suspend fun getFoodHistoryNowSlot(): List<FeedHistoryVo>
}
package com.paymong.wear.domain.usecase.slot

import androidx.lifecycle.LiveData
import com.paymong.wear.domain.repositroy.SlotRepository
import com.paymong.wear.domain.vo.SlotVo
import javax.inject.Inject

class GetSlotsUseCase @Inject constructor(
    private val slotRepository: SlotRepository
) {
    suspend operator fun invoke(): LiveData<List<SlotVo>> {
        return slotRepository.getSlotsLive()
    }
}
package com.paymong.wear.domain.usecase.slot

import com.paymong.wear.domain.repositroy.ManagementRepository
import com.paymong.wear.domain.repositroy.SlotRepository
import javax.inject.Inject

class EvoluteNowSlotUseCase @Inject constructor(
    private val slotRepository: SlotRepository,
    private val managementRepository: ManagementRepository
) {
    suspend operator fun invoke() {
        val slotModel = slotRepository.getNowSlot()
        val mongId = slotModel.mongId

        managementRepository.evolution(mongId = mongId)
    }
}
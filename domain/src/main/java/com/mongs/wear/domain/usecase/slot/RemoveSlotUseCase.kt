package com.mongs.wear.domain.usecase.slot

import com.mongs.wear.domain.repositroy.ManagementRepository
import com.mongs.wear.domain.repositroy.SlotRepository
import javax.inject.Inject

class RemoveSlotUseCase @Inject constructor(
    private val slotRepository: SlotRepository,
    private val managementRepository: ManagementRepository,
) {
    suspend operator fun invoke() {
        val slotModel = slotRepository.getNowSlot()
        val mongId = slotModel.mongId

        managementRepository.delete(mongId = mongId)
    }
}
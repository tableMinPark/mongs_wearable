package com.mongs.wear.domain.slot.usecase

import com.mongs.wear.domain.slot.repository.SlotRepository
import javax.inject.Inject

class SetNowSlotUseCase @Inject constructor(
    private val slotRepository: SlotRepository,
) {
    suspend operator fun invoke(mongId: Long) {
        slotRepository.setNowSlot(
            mongId = mongId
        )
        slotRepository.updateCurrentSlot()
    }
}
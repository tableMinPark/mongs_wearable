package com.mongs.wear.domain.slot.usecase

import com.mongs.wear.domain.slot.repository.SlotRepository
import javax.inject.Inject

class SetCurrentSlotUseCase @Inject constructor(
    private val slotRepository: SlotRepository,
) {
    suspend operator fun invoke(mongId: Long) {
        slotRepository.setCurrentSlot(mongId = mongId)
    }
}
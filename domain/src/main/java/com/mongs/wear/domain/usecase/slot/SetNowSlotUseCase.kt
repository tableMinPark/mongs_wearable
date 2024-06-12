package com.mongs.wear.domain.usecase.slot

import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.repositroy.SlotRepository
import javax.inject.Inject

class SetNowSlotUseCase @Inject constructor(
    private val slotRepository: SlotRepository,
) {
    suspend operator fun invoke(mongId: Long) {
        try {
            slotRepository.setNowSlot(mongId = mongId)
            slotRepository.syncNowSlot()
        } catch (e: RepositoryException) {
            throw UseCaseException(e.errorCode)
        }
    }
}
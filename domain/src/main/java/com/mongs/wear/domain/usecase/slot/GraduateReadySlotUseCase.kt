package com.mongs.wear.domain.usecase.slot

import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.repositroy.ManagementRepository
import com.mongs.wear.domain.repositroy.SlotRepository
import javax.inject.Inject

class GraduateReadySlotUseCase @Inject constructor(
    private val slotRepository: SlotRepository,
    private val managementRepository: ManagementRepository,
) {
    suspend operator fun invoke() {
        try {
            val slotModel = slotRepository.getNowSlot()
            managementRepository.graduateReadyMong(mongId = slotModel.mongId)
        } catch (e: RepositoryException) {
            throw UseCaseException(e.errorCode)
        }
    }
}
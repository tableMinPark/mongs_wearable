package com.mongs.wear.domain.usecase.slot

import com.mongs.wear.domain.repositroy.ManagementRepository
import com.mongs.wear.domain.repositroy.SlotRepository
import javax.inject.Inject

class TrainingSlotUseCase @Inject constructor(
    private val slotRepository: SlotRepository,
    private val managementRepository: ManagementRepository,
) {
    suspend operator fun invoke(trainingCode: String, score: Int) {
        val slotModel = slotRepository.getNowSlot()
        managementRepository.trainingMong(
            mongId = slotModel.mongId,
            trainingCode = trainingCode,
            score = score,
        )
    }
}
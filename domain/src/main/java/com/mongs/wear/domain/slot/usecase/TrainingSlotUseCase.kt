package com.mongs.wear.domain.slot.usecase

import com.mongs.wear.domain.management.repository.ManagementRepository
import com.mongs.wear.domain.slot.repository.SlotRepository
import javax.inject.Inject

class TrainingSlotUseCase @Inject constructor(
    private val slotRepository: SlotRepository,
    private val managementRepository: ManagementRepository,
) {
    suspend operator fun invoke(trainingCode: String, score: Int) {
        val slotModel = slotRepository.getNowSlot()
//        managementRepository.trainingMong(
//            mongId = slotModel.mongId,
//            trainingCode = trainingCode,
//            score = score,
//        )
    }
}
package com.mongs.wear.domain.slot.usecase

import android.util.Log
import com.mongs.wear.domain.management.repository.ManagementRepository
import com.mongs.wear.domain.slot.repository.SlotRepository
import javax.inject.Inject

class TrainingSlotUseCase @Inject constructor(
    private val slotRepository: SlotRepository,
    private val managementRepository: ManagementRepository,
) {
    suspend operator fun invoke(trainingCode: String, score: Int) {

        Log.d("TrainingSlotUseCase", "invoke: $trainingCode, $score")
    }
}
package com.mongs.wear.domain.usecase.slot

import com.mongs.wear.domain.repositroy.ManagementRepository
import javax.inject.Inject

class EvoluteNowSlotUseCase @Inject constructor(
    private val managementRepository: ManagementRepository,
) {
    suspend operator fun invoke(mongId: Long) {
        managementRepository.evolutionMong(mongId = mongId)
    }
}
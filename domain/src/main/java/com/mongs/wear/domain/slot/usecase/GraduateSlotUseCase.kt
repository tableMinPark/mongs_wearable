package com.mongs.wear.domain.slot.usecase

import com.mongs.wear.domain.management.repository.ManagementRepository
import javax.inject.Inject

class GraduateSlotUseCase @Inject constructor(
    private val managementRepository: ManagementRepository,
) {
    suspend operator fun invoke(mongId: Long) {
        managementRepository.graduateMong(mongId = mongId)
    }
}
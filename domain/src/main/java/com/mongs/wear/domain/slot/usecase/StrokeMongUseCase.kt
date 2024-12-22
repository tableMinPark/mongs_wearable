package com.mongs.wear.domain.slot.usecase

import com.mongs.wear.domain.management.repository.ManagementRepository
import javax.inject.Inject

class StrokeMongUseCase @Inject constructor(
    private val managementRepository: ManagementRepository,
) {
    suspend operator fun invoke(mongId: Long) {

        managementRepository.strokeMong(mongId = mongId)

        managementRepository.setIsHappy(mongId = mongId)
    }
}
package com.mongs.wear.domain.management.usecase

import com.mongs.wear.domain.management.repository.ManagementRepository
import javax.inject.Inject

class PoopCleanMongUseCase @Inject constructor(
    private val managementRepository: ManagementRepository,
) {
    suspend operator fun invoke(mongId: Long) {

        managementRepository.poopCleanMong(mongId = mongId)

        managementRepository.setIsPoopCleaning(mongId = mongId)
    }
}
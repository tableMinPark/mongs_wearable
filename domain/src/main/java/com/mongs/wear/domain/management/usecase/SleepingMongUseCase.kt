package com.mongs.wear.domain.management.usecase

import com.mongs.wear.domain.management.repository.ManagementRepository
import javax.inject.Inject

class SleepingMongUseCase @Inject constructor(
    private val managementRepository: ManagementRepository,
) {
    suspend operator fun invoke(mongId: Long) {
        managementRepository.sleepingMong(mongId = mongId)
    }
}
package com.mongs.wear.domain.slot.usecase

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.management.repository.ManagementRepository
import com.mongs.wear.domain.slot.exception.InvalidStrokeMongException
import javax.inject.Inject

class StrokeMongUseCase @Inject constructor(
    private val managementRepository: ManagementRepository,
) {
    suspend operator fun invoke(mongId: Long) {

        try {
            managementRepository.strokeMong(mongId = mongId)

            managementRepository.setIsHappy(mongId = mongId)

        } catch (_: ErrorException) {

            throw InvalidStrokeMongException()
        }
    }
}
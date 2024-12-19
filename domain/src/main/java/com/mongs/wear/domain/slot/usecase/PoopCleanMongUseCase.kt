package com.mongs.wear.domain.slot.usecase

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.management.repository.ManagementRepository
import com.mongs.wear.domain.slot.exception.InvalidPoopCleanMongException
import javax.inject.Inject

class PoopCleanMongUseCase @Inject constructor(
    private val managementRepository: ManagementRepository,
) {
    suspend operator fun invoke(mongId: Long) {

        try {
            managementRepository.poopCleanMong(mongId = mongId)

            managementRepository.setIsPoopCleaning(mongId = mongId)

        } catch (_: ErrorException) {

            throw InvalidPoopCleanMongException()
        }
    }
}
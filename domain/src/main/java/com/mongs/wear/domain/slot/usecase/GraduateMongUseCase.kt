package com.mongs.wear.domain.slot.usecase

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.management.repository.ManagementRepository
import com.mongs.wear.domain.slot.exception.InvalidGraduateMongException
import javax.inject.Inject

class GraduateMongUseCase @Inject constructor(
    private val managementRepository: ManagementRepository,
) {
    suspend operator fun invoke(mongId: Long) {

        try {
            managementRepository.graduateMong(mongId = mongId)

        } catch (_: ErrorException) {

            throw InvalidGraduateMongException()
        }
    }
}
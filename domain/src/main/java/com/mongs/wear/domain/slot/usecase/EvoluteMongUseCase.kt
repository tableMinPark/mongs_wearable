package com.mongs.wear.domain.slot.usecase

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.management.repository.ManagementRepository
import com.mongs.wear.domain.slot.exception.InvalidEvolutionMongException
import javax.inject.Inject

class EvoluteMongUseCase @Inject constructor(
    private val managementRepository: ManagementRepository,
) {
    suspend operator fun invoke(mongId: Long) {

        try {
            managementRepository.evolutionMong(mongId = mongId)

        } catch (_: ErrorException) {

            throw InvalidEvolutionMongException()
        }
    }
}
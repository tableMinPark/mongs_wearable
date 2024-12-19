package com.mongs.wear.domain.slot.usecase

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.management.repository.ManagementRepository
import com.mongs.wear.domain.slot.exception.InvalidCreateMongException
import javax.inject.Inject

class CreateMongUseCase @Inject constructor(
    private val managementRepository: ManagementRepository,
) {
    suspend operator fun invoke(name: String, sleepStart: String, sleepEnd: String) {
        try {
            managementRepository.createMong(
                name = name,
                sleepStart = sleepStart,
                sleepEnd = sleepEnd
            )
        } catch (_: ErrorException) {

            throw InvalidCreateMongException()
        }
    }
}
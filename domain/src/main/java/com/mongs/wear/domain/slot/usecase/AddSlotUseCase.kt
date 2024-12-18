package com.mongs.wear.domain.slot.usecase

import com.mongs.wear.domain.management.repository.ManagementRepository
import javax.inject.Inject

class AddSlotUseCase @Inject constructor(
    private val managementRepository: ManagementRepository,
) {
    suspend operator fun invoke(name: String, sleepStart: String, sleepEnd: String) {
        managementRepository.createMong(name = name, sleepStart = sleepStart, sleepEnd = sleepEnd)
    }
}
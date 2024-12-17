package com.mongs.wear.domain.usecase.slot

import com.mongs.wear.domain.repositroy.ManagementRepository
import javax.inject.Inject

class AddSlotUseCase @Inject constructor(
    private val managementRepository: ManagementRepository,
) {
    suspend operator fun invoke(name: String, sleepStart: String, sleepEnd: String) {
        managementRepository.createMong(name = name, sleepStart = sleepStart, sleepEnd = sleepEnd)
    }
}
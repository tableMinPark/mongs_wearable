package com.paymong.wear.domain.usecase.slot

import com.paymong.wear.domain.repositroy.ManagementRepository
import javax.inject.Inject

class AddSlotUseCase @Inject constructor(
    private val managementRepository: ManagementRepository
) {
    suspend operator fun invoke(name: String, sleepStart: String, sleepEnd: String) {
        managementRepository.add(name = name, sleepStart = sleepStart, sleepEnd = sleepEnd)
    }
}
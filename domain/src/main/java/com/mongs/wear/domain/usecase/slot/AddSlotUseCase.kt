package com.mongs.wear.domain.usecase.slot

import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.repositroy.ManagementRepository
import javax.inject.Inject

class AddSlotUseCase @Inject constructor(
    private val managementRepository: ManagementRepository
) {
    suspend operator fun invoke(name: String, sleepStart: String, sleepEnd: String) {
        try {
            managementRepository.addMong(name = name, sleepStart = sleepStart, sleepEnd = sleepEnd)
        } catch (e: RepositoryException) {
            throw UseCaseException(e.errorCode)
        }
    }
}
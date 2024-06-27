package com.mongs.wear.domain.usecase.slot

import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.repositroy.ManagementRepository
import com.mongs.wear.domain.repositroy.SlotRepository
import javax.inject.Inject

class RemoveSlotUseCase @Inject constructor(
    private val managementRepository: ManagementRepository,
) {
    suspend operator fun invoke(mongId: Long) {
        try {
            managementRepository.deleteMong(mongId = mongId)
        } catch (e: RepositoryException) {
            throw UseCaseException(e.errorCode)
        }
    }
}
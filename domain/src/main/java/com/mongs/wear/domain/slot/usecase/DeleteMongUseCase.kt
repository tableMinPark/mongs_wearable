package com.mongs.wear.domain.slot.usecase

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.management.repository.ManagementRepository
import com.mongs.wear.domain.slot.exception.InvalidCreateMongException
import com.mongs.wear.domain.slot.exception.InvalidDeleteMongException
import javax.inject.Inject

class DeleteMongUseCase @Inject constructor(
    private val managementRepository: ManagementRepository,
) {
    suspend operator fun invoke(mongId: Long) {

        try {
            managementRepository.deleteMong(mongId = mongId)

        } catch (_: ErrorException) {

            throw InvalidDeleteMongException()
        }
    }
}
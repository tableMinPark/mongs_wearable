package com.mongs.wear.domain.usecase.slot

import com.mongs.wear.domain.code.FeedbackCode
import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.repositroy.FeedbackRepository
import com.mongs.wear.domain.repositroy.ManagementRepository
import javax.inject.Inject

class EvoluteNowSlotUseCase @Inject constructor(
    private val managementRepository: ManagementRepository,
    private val feedbackRepository: FeedbackRepository,
) {
    suspend operator fun invoke(mongId: Long) {
        try {
            managementRepository.evolutionMong(mongId = mongId)
        } catch (e: RepositoryException) {
            feedbackRepository.addFeedbackLog(
                groupCode = FeedbackCode.MANAGEMENT.groupCode,
                location = "EvoluteNowSlotUseCase",
                message = e.stackTrace.contentDeepToString(),
            )

            throw UseCaseException(e.errorCode, e)
        }
    }
}
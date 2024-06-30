package com.mongs.wear.domain.usecase.feedback

import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.repositroy.FeedbackRepository
import java.time.LocalDateTime
import javax.inject.Inject

class RemoveFeedbackLogUseCase @Inject constructor(
    private val feedbackRepository: FeedbackRepository,
) {
    suspend operator fun invoke() {
        try {
            val now = LocalDateTime.now()
            feedbackRepository.removeFeedbackLog(createdAt = now.minusWeeks(1))
        } catch (e: RepositoryException) {
            throw UseCaseException(e.errorCode, e)
        }
    }
}
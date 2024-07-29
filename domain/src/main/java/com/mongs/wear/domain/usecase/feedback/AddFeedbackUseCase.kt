package com.mongs.wear.domain.usecase.feedback

import com.mongs.wear.domain.code.FeedbackCode
import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.repositroy.FeedbackRepository
import javax.inject.Inject

class AddFeedbackUseCase @Inject constructor(
    private val feedbackRepository: FeedbackRepository,
) {
    suspend operator fun invoke(feedbackCode: FeedbackCode) {
        try {
            feedbackRepository.addFeedback(
                groupCode = feedbackCode.groupCode,
                message = "${feedbackCode.message}:${feedbackCode.secondaryMessage}"
            )
        } catch (e: RepositoryException) {
            throw UseCaseException(e.errorCode, e)
        }
    }
}
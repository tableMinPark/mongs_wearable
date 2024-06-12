package com.mongs.wear.domain.usecase.feedback

import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.repositroy.FeedbackRepository
import javax.inject.Inject

class AddFeedbackUseCase @Inject constructor(
    private val feedbackRepository: FeedbackRepository
) {
    suspend operator fun invoke(groupCode: String, message: String) {
        try {
            feedbackRepository.addFeedback(groupCode = groupCode, message = message)
        } catch (e: RepositoryException) {
            throw UseCaseException(e.errorCode)
        }
    }
}
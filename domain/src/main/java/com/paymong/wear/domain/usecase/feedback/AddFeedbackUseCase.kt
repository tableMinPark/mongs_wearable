package com.paymong.wear.domain.usecase.feedback

import com.paymong.wear.domain.repositroy.FeedbackRepository
import javax.inject.Inject

class AddFeedbackUseCase @Inject constructor(
    private val feedbackRepository: FeedbackRepository
) {
    suspend operator fun invoke(code: String, message: String) {
        feedbackRepository.addFeedback(code = code, message = message)
    }
}
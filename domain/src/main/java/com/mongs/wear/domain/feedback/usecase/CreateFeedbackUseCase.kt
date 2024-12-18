package com.mongs.wear.domain.feedback.usecase

import com.mongs.wear.domain.feedback.repository.FeedbackRepository
import javax.inject.Inject

class CreateFeedbackUseCase @Inject constructor(
    private val feedbackRepository: FeedbackRepository,
) {
    suspend operator fun invoke(title: String, content: String) {

        feedbackRepository.createFeedback(title = title, content = content)
    }
}
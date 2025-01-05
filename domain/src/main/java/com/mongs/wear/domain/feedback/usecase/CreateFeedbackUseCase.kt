package com.mongs.wear.domain.feedback.usecase

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.feedback.exception.CreateFeedbackException
import com.mongs.wear.domain.feedback.repository.FeedbackRepository
import com.mongs.wear.domain.global.usecase.BaseParamUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreateFeedbackUseCase @Inject constructor(
    private val feedbackRepository: FeedbackRepository,
) : BaseParamUseCase<CreateFeedbackUseCase.Param, Unit>() {

    override suspend fun execute(param: Param) {

        withContext(Dispatchers.IO) {
            feedbackRepository.createFeedback(
                title = param.title,
                content = param.content,
            )
        }
    }

    data class Param(

        val title: String,

        val content: String,
    )

    override fun handleException(exception: ErrorException) {
        super.handleException(exception)
        throw CreateFeedbackException()
    }
}
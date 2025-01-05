package com.mongs.wear.data.user.repository

import com.mongs.wear.data.user.api.FeedbackApi
import com.mongs.wear.data.user.dto.request.CreateFeedbackRequestDto
import com.mongs.wear.data.user.exception.CreateFeedbackException
import com.mongs.wear.domain.feedback.repository.FeedbackRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeedbackRepositoryImpl @Inject constructor(
    private val feedbackApi: FeedbackApi
) : FeedbackRepository {

    /**
     * 오륲 신고 등록
     */
    override suspend fun createFeedback(title: String, content: String) {

        val deviceName = android.os.Build.MODEL

        val response = feedbackApi.createFeedback(
            createFeedbackRequestDto = CreateFeedbackRequestDto(
                deviceName = deviceName,
                title = title,
                content = content
            )
        )

        if (!response.isSuccessful) {
            throw CreateFeedbackException()
        }
    }
}
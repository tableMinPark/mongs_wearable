package com.paymong.wear.data.repository

import com.paymong.wear.data.api.client.FeedbackApi
import com.paymong.wear.data.dto.feedback.req.RegisterFeedbackReqDto
import com.paymong.wear.data.room.client.RoomDB
import com.paymong.wear.data.room.entity.FeedbackLog
import com.paymong.wear.data.vo.FeedbackLogVo
import com.paymong.wear.data.vo.FeedbackVo
import com.paymong.wear.domain.error.RepositoryErrorCode
import com.paymong.wear.domain.exception.ApiException
import com.paymong.wear.domain.repositroy.FeedbackRepository
import javax.inject.Inject

class FeedbackRepositoryImpl @Inject constructor(
    private val roomDB: RoomDB,
    private val feedbackApi: FeedbackApi
): FeedbackRepository {
    override suspend fun addFeedbackLog(groupCode: String, location: String, message: String) {
        roomDB.feedbackLogDao().register(
            FeedbackLog(
                groupCode = groupCode,
                location = location,
                message = message,
            )
        )
    }
    override suspend fun addFeedback(groupCode: String, message: String) {
        val feedbackLogs = roomDB.feedbackLogDao().findByGroupCode(groupCode = groupCode)

        val res = feedbackApi.registerFeedback(
            RegisterFeedbackReqDto(
                feedback = FeedbackVo(
                    message = message
                ),
                feedbackLogList = feedbackLogs.map { feedbackLog ->
                    FeedbackLogVo(
                        feedbackLogId = feedbackLog.id,
                        location = feedbackLog.location,
                        message = feedbackLog.message,
                        createdAt = feedbackLog.createdAt,
                    )
                }
            )
        )

        if (!res.isSuccessful) {
            throw ApiException(RepositoryErrorCode.FEEDBACK_FAIL)
        }

    }
}
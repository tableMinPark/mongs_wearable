package com.mongs.wear.data.repository

import com.mongs.wear.data.api.client.FeedbackApi
import com.mongs.wear.data.dto.feedback.req.RegisterFeedbackReqDto
import com.mongs.wear.data.room.client.RoomDB
import com.mongs.wear.data.room.entity.FeedbackLog
import com.mongs.wear.data.vo.FeedbackLogVo
import com.mongs.wear.data.vo.FeedbackVo
import com.mongs.wear.domain.error.RepositoryErrorCode
import com.mongs.wear.domain.exception.parent.ApiException
import com.mongs.wear.domain.repositroy.FeedbackRepository
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
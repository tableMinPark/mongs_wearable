package com.mongs.wear.data.repository

import com.mongs.wear.data.api.client.FeedbackApi
import com.mongs.wear.data.dto.feedback.req.RegisterFeedbackReqDto
import com.mongs.wear.data.room.client.RoomDB
import com.mongs.wear.data.room.entity.FeedbackLog
import com.mongs.wear.data.vo.FeedbackLogVo
import com.mongs.wear.data.vo.FeedbackVo
import com.mongs.wear.domain.error.RepositoryErrorCode
import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.repositroy.FeedbackRepository
import javax.inject.Inject

class FeedbackRepositoryImpl @Inject constructor(
    private val feedbackApi: FeedbackApi,
    private val roomDB: RoomDB,
): FeedbackRepository {
    override suspend fun addFeedbackLog(groupCode: String, location: String, message: String) {
        try {
            roomDB.feedbackLogDao().insert(
                FeedbackLog(
                    groupCode = groupCode,
                    location = location,
                    message = message,
                )
            )
        } catch (e: RuntimeException) {
            throw RepositoryException(RepositoryErrorCode.ADD_FEEDBACK_LOG_FAIL)
        }
    }
    override suspend fun addFeedback(groupCode: String, message: String) {
        try {
            val feedbackLogs = roomDB.feedbackLogDao().selectByGroupCode(groupCode = groupCode)
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
                throw RepositoryException(RepositoryErrorCode.ADD_FEEDBACK_FAIL)
            }
        } catch (e: RuntimeException) {
            throw RepositoryException(RepositoryErrorCode.ADD_FEEDBACK_FAIL)
        }
    }
}
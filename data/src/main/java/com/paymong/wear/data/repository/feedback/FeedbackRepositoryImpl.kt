package com.paymong.wear.data.repository.feedback

import com.paymong.wear.data.api.feedback.FeedbackApi
import com.paymong.wear.data.api.feedback.dto.request.RegisterFeedbackReqDto
import com.paymong.wear.data.api.feedback.vo.FeedbackLogVo
import com.paymong.wear.data.api.feedback.vo.FeedbackVo
import com.paymong.wear.data.room.RoomDB
import com.paymong.wear.data.room.feedback.FeedbackLog
import com.paymong.wear.domain.error.FeedbackErrorCode
import com.paymong.wear.domain.exception.FeedbackException
import com.paymong.wear.domain.repository.feedback.FeedbackRepository
import javax.inject.Inject

class FeedbackRepositoryImpl @Inject constructor(
    private val roomDB: RoomDB,
    private val feedbackApi: FeedbackApi
) : FeedbackRepository {
    override suspend fun addFeedbackLog(groupCode: String, location: String, message: String) {
        roomDB.feedbackLogDao().register(FeedbackLog(
            groupCode = groupCode,
            location = location,
            message = message,
        ))
    }

    override suspend fun feedback(code: String, groupCode: String, message: String) {
        roomDB.feedbackLogDao().findByGroupCode(groupCode = groupCode).let { feedbackLogList ->

            val response = feedbackApi.registerFeedback(RegisterFeedbackReqDto(
                feedback = FeedbackVo(
                    code = code,
                    title = message,
                    content = "",
                ),
                feedbackLogList = feedbackLogList.map { feedbackLog ->
                    FeedbackLogVo(
                        id = feedbackLog.id,
                        location = feedbackLog.location,
                        message = feedbackLog.message,
                        createdAt = feedbackLog.createdAt
                    )
                }
            ))

            if (!response.isSuccessful) {
                throw FeedbackException(FeedbackErrorCode.REGISTER_FEEDBACK_FAIL)
            }
        }
    }
}
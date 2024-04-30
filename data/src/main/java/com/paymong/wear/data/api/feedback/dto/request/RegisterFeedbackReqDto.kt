package com.paymong.wear.data.api.feedback.dto.request

import com.paymong.wear.data.api.feedback.vo.FeedbackLogVo
import com.paymong.wear.data.api.feedback.vo.FeedbackVo

data class RegisterFeedbackReqDto(
    val feedback: FeedbackVo,
    val feedbackLogList: List<FeedbackLogVo>,
)

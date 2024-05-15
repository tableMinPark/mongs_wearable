package com.paymong.wear.data.dto.feedback.req

import com.paymong.wear.data.vo.FeedbackLogVo
import com.paymong.wear.data.vo.FeedbackVo

data class RegisterFeedbackReqDto(
    val feedback: FeedbackVo,
    val feedbackLogList: List<FeedbackLogVo>,
)

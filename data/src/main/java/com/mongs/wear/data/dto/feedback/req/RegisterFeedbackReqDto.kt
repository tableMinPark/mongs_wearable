package com.mongs.wear.data.dto.feedback.req

import com.mongs.wear.data.vo.FeedbackLogVo
import com.mongs.wear.data.vo.FeedbackVo

data class RegisterFeedbackReqDto(
    val feedback: FeedbackVo,
    val feedbackLogList: List<FeedbackLogVo>,
)

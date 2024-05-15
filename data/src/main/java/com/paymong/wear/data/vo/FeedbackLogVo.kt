package com.paymong.wear.data.vo

import java.time.LocalDateTime

data class FeedbackLogVo(
    val feedbackLogId: String,
    val location: String,
    val message: String,
    val createdAt: LocalDateTime,
)

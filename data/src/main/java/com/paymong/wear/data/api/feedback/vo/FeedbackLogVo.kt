package com.paymong.wear.data.api.feedback.vo

import java.time.LocalDateTime

data class FeedbackLogVo(
    val id: String,
    val location: String,
    val message: String,
    val createdAt: LocalDateTime,
)

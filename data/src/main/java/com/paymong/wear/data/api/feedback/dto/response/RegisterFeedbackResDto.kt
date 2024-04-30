package com.paymong.wear.data.api.feedback.dto.response

data class RegisterFeedbackResDto(
    val id: String,
    val accountId: Long,
    val deviceId: String,
    val code: String,
)

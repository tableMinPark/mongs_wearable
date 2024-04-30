package com.paymong.wear.data.api.auth.dto.response

data class LoginResDto(
    val accountId: Long,
    val accessToken: String,
    val refreshToken: String,
)

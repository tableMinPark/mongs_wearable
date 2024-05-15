package com.paymong.wear.data.dto.auth.res

data class LoginResDto(
    val accountId: Long,
    val accessToken: String,
    val refreshToken: String,
)

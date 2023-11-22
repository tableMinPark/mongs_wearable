package com.paymong.wear.domain.dto.response

data class LoginResDto(
    val accessToken: String,
    val refreshToken: String
)
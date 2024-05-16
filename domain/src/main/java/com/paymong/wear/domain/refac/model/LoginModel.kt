package com.paymong.wear.domain.refac.model

data class LoginModel(
    val accountId: Long,
    val accessToken: String,
    val refreshToken: String,
)

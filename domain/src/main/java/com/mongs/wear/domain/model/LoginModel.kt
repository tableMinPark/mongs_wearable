package com.mongs.wear.domain.model

data class LoginModel(
    val accountId: Long,
    val accessToken: String,
    val refreshToken: String,
)

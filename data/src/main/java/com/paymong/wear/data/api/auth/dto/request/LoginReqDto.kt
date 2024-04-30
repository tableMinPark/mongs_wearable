package com.paymong.wear.data.api.auth.dto.request

data class LoginReqDto(
    val deviceId: String,
    val email: String,
    val name: String,
)

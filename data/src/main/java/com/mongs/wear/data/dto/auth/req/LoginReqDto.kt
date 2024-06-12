package com.mongs.wear.data.dto.auth.req

data class LoginReqDto(
    val deviceId: String,
    val email: String,
    val name: String,
)

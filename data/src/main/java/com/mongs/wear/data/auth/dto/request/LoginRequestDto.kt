package com.mongs.wear.data.auth.dto.request

data class LoginRequestDto(

    val deviceId: String,

    val email: String,

    val socialAccountId: String,

    val appPackageName: String,

    val buildVersion: String,
)

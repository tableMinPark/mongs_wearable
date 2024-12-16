package com.mongs.wear.data_.auth.dto.request

data class LoginRequestDto(

    val deviceId: String,

    val email: String,

    val name: String,

    val appCode: String,

    val buildVersion: String,
)

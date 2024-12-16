package com.mongs.wear.data_.auth.dto.response

data class LoginResponseDto(

    val accountId: Long,

    val accessToken: String,

    val refreshToken: String,
)

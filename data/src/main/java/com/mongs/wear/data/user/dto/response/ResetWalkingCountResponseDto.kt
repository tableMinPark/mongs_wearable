package com.mongs.wear.data.user.dto.response

import java.time.LocalDateTime

data class ResetWalkingCountResponseDto(

    val consumeWalkingCount: Int,

    val walkingCount: Int,

    val deviceBootedDt: LocalDateTime,
)

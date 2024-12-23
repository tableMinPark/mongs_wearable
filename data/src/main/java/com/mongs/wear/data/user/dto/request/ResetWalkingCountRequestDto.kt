package com.mongs.wear.data.user.dto.request

import java.time.LocalDateTime

data class ResetWalkingCountRequestDto(

    val totalWalkingCount: Int,

    val deviceBootedDt: LocalDateTime,
)

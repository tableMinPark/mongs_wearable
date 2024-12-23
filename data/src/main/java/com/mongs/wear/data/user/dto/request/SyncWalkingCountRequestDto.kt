package com.mongs.wear.data.user.dto.request

import java.time.LocalDateTime

data class SyncWalkingCountRequestDto(

    val deviceId: String,

    val totalWalkingCount: Int,

    val deviceBootedDt: LocalDateTime,
)

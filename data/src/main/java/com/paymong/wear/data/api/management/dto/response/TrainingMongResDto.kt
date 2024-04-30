package com.paymong.wear.data.api.management.dto.response

import java.time.LocalDateTime

data class TrainingMongResDto(
    val mongId: Long,
    val strength: Double,
    val exp: Double,
    val payPoint: Int,
)

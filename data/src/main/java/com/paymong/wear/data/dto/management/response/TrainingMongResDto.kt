package com.paymong.wear.data.dto.management.response

import java.time.LocalDateTime

data class TrainingMongResDto(
    val accountId: Long,
    val mongId: Long,
    val weight: Double,
    val strength: Double,
    val satiety: Double,
    val healthy: Double,
    val sleep: Double,
    val exp: Double,
    val payPoint: Int,
)

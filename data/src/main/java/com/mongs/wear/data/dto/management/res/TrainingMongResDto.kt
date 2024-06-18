package com.mongs.wear.data.dto.management.res

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

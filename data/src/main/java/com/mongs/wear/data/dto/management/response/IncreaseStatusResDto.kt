package com.mongs.wear.data.dto.management.response

data class IncreaseStatusResDto(
    val accountId: Long,
    val mongId: Long,
    val weight: Double,
    val strength: Double,
    val satiety: Double,
    val healthy: Double,
    val sleep: Double,
)

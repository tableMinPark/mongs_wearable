package com.paymong.wear.data.api.management.dto.response

data class FeedMongResDto(
    val mongId: Long,
    val weight: Double,
    val strength: Double,
    val satiety: Double,
    val healthy: Double,
    val sleep: Double,
    val exp: Double,
    val payPoint: Int,
)

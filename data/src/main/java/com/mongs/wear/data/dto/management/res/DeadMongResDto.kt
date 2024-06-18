package com.mongs.wear.data.dto.management.res

data class DeadMongResDto(
    val accountId: Long,
    val mongId: Long,

    val shiftCode: String,
    val stateCode: String,

    val exp: Double,
    val weight: Double,
    val strength: Double,
    val satiety: Double,
    val healthy: Double,
    val sleep: Double,
    val poopCount: Int,
    val isSleeping: Boolean
)

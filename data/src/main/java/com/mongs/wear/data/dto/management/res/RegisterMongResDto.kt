package com.mongs.wear.data.dto.management.res

import java.time.LocalDateTime

data class RegisterMongResDto(
    val accountId: Long,
    val mongId: Long,
    val name: String,
    val mongCode: String,
    val weight: Double,
    val healthy: Double,
    val satiety: Double,
    val strength: Double,
    val sleep: Double,
    val exp: Double,
    val poopCount: Int,
    val isSleeping: Boolean,
    val stateCode: String,
    val shiftCode: String,
    val payPoint: Int,
    val born: LocalDateTime,
)

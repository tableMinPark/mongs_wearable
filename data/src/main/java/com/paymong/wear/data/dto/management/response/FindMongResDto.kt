package com.paymong.wear.data.dto.management.response

import java.time.LocalDateTime

data class FindMongResDto(
    val accountId: Long,
    val mongId: Long,
    val name: String,
    val mongCode: String,
    val weight: Double,
    val healthy: Double,
    val satiety: Double,
    val strength: Double,
    val sleep: Double,
    val poopCount: Int,
    val isSleeping: Boolean,
    val exp: Double,
    val stateCode: String,
    val shiftCode: String,
    val payPoint: Int,
    val born: LocalDateTime,
)

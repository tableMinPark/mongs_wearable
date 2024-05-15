package com.paymong.wear.data.dto.management.response

data class EvolutionMongResDto(
    val accountId: Long,
    val mongId: Long,
    val mongCode: String,
    val weight: Double,
    val strength: Double,
    val satiety: Double,
    val healthy: Double,
    val sleep: Double,
    val exp: Double,
    val shiftCode: String,
    val stateCode: String,
)

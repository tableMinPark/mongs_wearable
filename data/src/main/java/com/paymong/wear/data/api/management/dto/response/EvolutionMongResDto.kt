package com.paymong.wear.data.api.management.dto.response

data class EvolutionMongResDto(
    val mongId: Long,
    val mongCode: String,
    val weight: Double,
    val strength: Double,
    val satiety: Double,
    val healthy: Double,
    val sleep: Double,
    val shiftCode: String,
    val stateCode: String,
    val exp: Double,
)

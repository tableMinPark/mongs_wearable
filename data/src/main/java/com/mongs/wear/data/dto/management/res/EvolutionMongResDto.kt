package com.mongs.wear.data.dto.management.res

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

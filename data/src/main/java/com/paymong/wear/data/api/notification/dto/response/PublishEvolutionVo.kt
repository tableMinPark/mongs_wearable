package com.paymong.wear.data.api.notification.dto.response

data class PublishEvolutionVo(
    val mongId: Long,
    val mongCode: String,
    val weight: Double,
    val strength: Double,
    val satiety: Double,
    val healthy: Double,
    val sleep: Double,
    val stateCode: String,
    val shiftCode: String,
    val exp: Double,
)

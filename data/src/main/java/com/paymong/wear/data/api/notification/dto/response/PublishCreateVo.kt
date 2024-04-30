package com.paymong.wear.data.api.notification.dto.response

import java.time.LocalDateTime

data class PublishCreateVo(
    val mongId: Long,
    val name: String,
    val mongCode: String,
    val weight: Double,
    val strength: Double,
    val satiety: Double,
    val healthy: Double,
    val sleep: Double,
    val poopCount: Int,
    val isSleeping: Boolean,
    val exp: Double,
    val stateCode: String,
    val shiftCode: String,
    val payPoint: Int,
    val born: LocalDateTime,
)

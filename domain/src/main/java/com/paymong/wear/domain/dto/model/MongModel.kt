package com.paymong.wear.domain.dto.model

import java.time.LocalDateTime

data class MongModel(
    var slotId: Long,
    var mongId: Long,
    var born: LocalDateTime,
    var weight: Int,
    var mongCode: String,
    var nextMongCode: String,

    var stateCode: String,
    var nextStateCode: String,
    var poopCount: Int,

    var health: Float,
    var satiety: Float,
    var strength: Float,
    var sleep: Float,
)
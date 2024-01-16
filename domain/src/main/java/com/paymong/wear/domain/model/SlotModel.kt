package com.paymong.wear.domain.model

import java.time.LocalDateTime

data class SlotModel(
    var slotId: Long = 0L,
    var mongId: Long = -1L,
    var born: LocalDateTime = LocalDateTime.now(),
    var weight: Int = 0,
    var mongCode: String = "CH444",
    var nextMongCode: String = "CH444",

    var stateCode: String = "CD444",
    var nextStateCode: String = "CD444",
    var shiftCode: String = "SH444",
    var poopCount: Int = 0,

    var health: Float = 0f,
    var satiety: Float = 0f,
    var strength: Float = 0f,
    var sleep: Float = 0f,
)
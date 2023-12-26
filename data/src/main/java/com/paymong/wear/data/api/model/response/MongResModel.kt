package com.paymong.wear.data.api.model.response

import java.time.LocalDateTime

data class MongResModel(
    val mongId: Long,
    val born: LocalDateTime,
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
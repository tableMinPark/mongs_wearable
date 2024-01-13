package com.paymong.wear.data.mqtt.model.response

data class StatusResModel(
    val mongId: Long,

    var health: Float,
    var satiety: Float,
    var strength: Float,
    var sleep: Float,
)

package com.paymong.wear.data.mqtt.model.response

data class EvolutionResModel(
    val mongId: Long,
    val stateCode: String,
    val nextMongCode: String
)

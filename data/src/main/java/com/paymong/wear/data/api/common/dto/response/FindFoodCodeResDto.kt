package com.paymong.wear.data.api.common.dto.response

data class FindFoodCodeResDto(
    val code: String,
    val name: String,
    val groupCode: String,
    val price: Int,
    val addWeight: Double,
    val addStrength: Double,
    val addSatiety: Double,
    val addHealthy: Double,
    val addSleep: Double,
    val buildVersion: String,
)

package com.paymong.wear.domain.vo

data class FoodVo(
    val code: String,
    val name: String,
    val price: Int,
    val addWeight: Double,
    val addStrength: Double,
    val addSatiety: Double,
    val addHealthy: Double,
    val addSleep: Double,
    val isCanBuy: Boolean,
)

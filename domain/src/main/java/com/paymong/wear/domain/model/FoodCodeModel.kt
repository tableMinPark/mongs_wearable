package com.paymong.wear.domain.model

data class FoodCodeModel(
    val code: String,
    val name: String,
    val price: Int,
    val addWeight: Double,
    val addStrength: Double,
    val addSatiety: Double,
    val addHealthy: Double,
    val addSleep: Double,
    val isCanBuy: Boolean = true
)

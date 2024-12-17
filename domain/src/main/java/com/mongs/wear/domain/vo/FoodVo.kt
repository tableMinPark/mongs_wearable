package com.mongs.wear.domain.vo

data class FoodVo(

    val code: String = "",

    val name: String = "",

    val price: Int = 0,

    val addWeight: Double = 0.0,

    val addStrength: Double = 0.0,

    val addSatiety: Double = 0.0,

    val addHealthy: Double = 0.0,

    val addSleep: Double = 0.0,

    val isCanBuy: Boolean = false,
)

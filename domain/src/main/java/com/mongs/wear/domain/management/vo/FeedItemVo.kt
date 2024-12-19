package com.mongs.wear.domain.management.vo

data class FeedItemVo(

    val foodTypeCode: String = "",

    val foodTypeName: String = "",

    val price: Int = 0,

    val isCanBuy: Boolean = false,

    val addWeightValue: Double = 0.0,

    val addStrengthValue: Double = 0.0,

    val addSatietyValue: Double = 0.0,

    val addHealthyValue: Double = 0.0,

    val addFatigueValue: Double = 0.0,
)

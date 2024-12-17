package com.mongs.wear.data.manager.dto.etc

data class GetFeedItemDto(

    val foodTypeCode: String,

    val foodTypeGroupCode: String,

    val foodTypeName: String,

    val price: Int,

    val isCanBuy: Boolean,

    val addWeightValue: Double,

    val addStrengthValue: Double,

    val addSatietyValue: Double,

    val addHealthyValue: Double,

    val addFatigueValue: Double,
)

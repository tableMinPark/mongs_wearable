package com.mongs.wear.data.manager.dto.response

import com.mongs.wear.core.enums.MongStatusCode

data class MongStatusObserveResponseDto(

    val statusCode: MongStatusCode,

    val expRatio: Double,

    val weight: Double,

    val strengthRatio: Double,

    val satietyRatio: Double,

    val healthyRatio: Double,

    val fatigueRatio: Double,

    val poopCount: Int,
)

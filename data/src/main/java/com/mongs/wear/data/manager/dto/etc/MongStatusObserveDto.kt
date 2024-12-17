package com.mongs.wear.data.manager.dto.etc

import com.mongs.wear.core.enums.MongStatusCode

data class MongStatusObserveDto(

    val statusCode: MongStatusCode,

    val expRatio: Double,

    val weight: Double,

    val strengthRatio: Double,

    val satietyRatio: Double,

    val healthyRatio: Double,

    val fatigueRatio: Double,

    val poopCount: Int,
)

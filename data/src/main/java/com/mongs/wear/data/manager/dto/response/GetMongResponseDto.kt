package com.mongs.wear.data.manager.dto.response

import com.mongs.wear.core.enums.MongStateCode
import com.mongs.wear.core.enums.MongStatusCode

data class GetMongResponseDto(

    val mongId: Long,

    val mongName: String,

    val mongTypeCode: String,

    val payPoint: Int,

    val weight: Double,

    val expRatio: Double,

    val strengthRatio: Double,

    val satietyRatio: Double,

    val healthyRatio: Double,

    val fatigueRatio: Double,

    val poopCount: Int,

    val stateCode: MongStateCode,

    val statusCode: MongStatusCode,

    val isSleep: Boolean,
)

package com.mongs.wear.data.manager.dto.response

import com.mongs.wear.core.enums.MongStateCode
import com.mongs.wear.core.enums.MongStatusCode
import java.time.LocalDateTime

data class GetMongResponseDto(

    val mongId: Long,

    val mongName: String,

    val mongTypeCode: String,

    val payPoint: Int,

    val stateCode: MongStateCode,

    val isSleep: Boolean,

    val statusCode: MongStatusCode,

    val expRatio: Double,

    val weight: Double,

    val strengthRatio: Double,

    val satietyRatio: Double,

    val healthyRatio: Double,

    val fatigueRatio: Double,

    val poopCount: Int,

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime,
)

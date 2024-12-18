package com.mongs.wear.domain.slot.model

import com.mongs.wear.core.enums.MongStateCode
import com.mongs.wear.core.enums.MongStatusCode
import java.time.LocalDateTime

data class SlotModel(

    val mongId: Long,

    val mongName: String,

    val payPoint: Int,

    val mongTypeCode: String,

    val createdAt: LocalDateTime,

    val weight: Double,

    val expRatio: Double,

    val healthyRatio: Double,

    val satietyRatio: Double,

    val strengthRatio: Double,

    val fatigueRatio: Double,

    val poopCount: Int,

    val stateCode: MongStateCode,

    val statusCode: MongStatusCode,

    val isSleeping: Boolean,

    val isCurrent: Boolean,

    val graduateCheck: Boolean,

    val isHappy: Boolean,

    val isEating: Boolean,

    val isPoopCleaning: Boolean,
)
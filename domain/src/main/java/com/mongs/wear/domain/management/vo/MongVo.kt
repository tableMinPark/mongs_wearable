package com.mongs.wear.domain.management.vo

import com.mongs.wear.core.enums.MongStateCode
import com.mongs.wear.core.enums.MongStatusCode
import java.time.LocalDateTime

data class MongVo(

    val mongId: Long = 0L,

    val mongName: String = "",

    val mongTypeCode: String = "CH444",

    val exp: Double = 0.0,

    val weight: Double = 0.0,

    val healthy: Double = 0.0,

    val satiety: Double = 0.0,

    val strength: Double = 0.0,

    val sleep: Double = 0.0,

    val poopCount: Int = 0,

    val isSleeping: Boolean = false,

    val stateCode: MongStateCode = MongStateCode.EMPTY,

    val statusCode: MongStatusCode = MongStatusCode.EMPTY,

    val payPoint: Int = 0,

    val born: LocalDateTime = LocalDateTime.now(),

    val graduateCheck: Boolean = false,

    val isHappy: Boolean = false,

    val isEating: Boolean = false,

    val isPoopCleaning: Boolean = false,

    val isCurrent: Boolean = false,
)
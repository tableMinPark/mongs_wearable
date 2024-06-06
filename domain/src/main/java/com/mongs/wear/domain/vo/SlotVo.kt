package com.mongs.wear.domain.vo

import com.mongs.wear.domain.code.ShiftCode
import com.mongs.wear.domain.code.StateCode
import java.time.LocalDateTime

data class SlotVo(
    val mongId: Long = 0L,
    val name: String = "",
    val mongCode: String = "CH444",
    val weight: Double = 0.0,
    val healthy: Double = 0.0,
    val satiety: Double = 0.0,
    val strength: Double = 0.0,
    val sleep: Double = 0.0,
    val poopCount: Int = 0,
    val isSleeping: Boolean = false,
    val exp: Double = 0.0,
    val stateCode: StateCode = StateCode.EMPTY,
    val shiftCode: ShiftCode = ShiftCode.EMPTY,
    val payPoint: Int = 0,
    val born: LocalDateTime = LocalDateTime.now(),

    val isHappy: Boolean = false,
    val isEating: Boolean = false,
    val isSelected: Boolean = false,

    val isGraduateCheck: Boolean = false,
)
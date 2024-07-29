package com.mongs.wear.domain.model

import com.mongs.wear.domain.code.ShiftCode
import com.mongs.wear.domain.code.StateCode
import java.time.LocalDateTime

data class SlotModel(
    val mongId: Long,
    val name: String,
    val mongCode: String,
    val weight: Double,
    val healthy: Double,
    val satiety: Double,
    val strength: Double,
    val sleep: Double,
    val poopCount: Int,
    val isSleeping: Boolean,
    val exp: Double,
    val stateCode: StateCode,
    val shiftCode: ShiftCode,
    val payPoint: Int,
    val born: LocalDateTime,

    val isHappy: Boolean,
    val isEating: Boolean,
    val isPoopCleaning: Boolean,
    val isSelected: Boolean,

    val isGraduateCheck: Boolean,
)
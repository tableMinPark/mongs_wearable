package com.paymong.wear.domain.refac.vo

import com.paymong.wear.domain.DefaultValue
import com.paymong.wear.domain.code.ShiftCode
import com.paymong.wear.domain.code.StateCode
import java.time.LocalDateTime

data class SlotVo(
    val mongId: Long = DefaultValue.MONG_ID,
    val name: String = DefaultValue.NAME,
    val mongCode: String = DefaultValue.MONG_CODE,
    val weight: Double = DefaultValue.WEIGHT,
    val healthy: Double = DefaultValue.HEALTHY,
    val satiety: Double = DefaultValue.SATIETY,
    val strength: Double = DefaultValue.STRENGTH,
    val sleep: Double = DefaultValue.SLEEP,
    val poopCount: Int = DefaultValue.POOP_COUNT,
    val isSleeping: Boolean = DefaultValue.IS_SLEEPING,
    val exp: Double = DefaultValue.EXP,
    val stateCode: StateCode = DefaultValue.STATE_CODE,
    val shiftCode: ShiftCode = DefaultValue.SHIFT_CODE,
    val payPoint: Int = DefaultValue.PAY_POINT,
    val born: LocalDateTime = DefaultValue.BORN,

    val isHappy: Boolean = DefaultValue.IS_HAPPY,
    val isEating: Boolean = DefaultValue.IS_EATING,
    val isSelected: Boolean = DefaultValue.IS_SELECTED,

    val isGraduateCheck: Boolean = false
)
package com.paymong.wear.domain

import com.paymong.wear.domain.code.ShiftCode
import com.paymong.wear.domain.code.StateCode
import com.paymong.wear.domain.repository.common.vo.MapCodeVo
import com.paymong.wear.domain.repository.common.vo.MongCodeVo
import com.paymong.wear.domain.repository.slot.vo.SlotVo
import java.time.LocalDateTime

object DefaultValue {
    /** for UI **/
    const val LOAD_DELAY = 300L

    const val SOUND = 0.5f
    const val BACKGROUND_MAP_CODE = "MP000"
    const val MAX_SLOT = 1
    const val STAR_POINT = 0

    const val MONG_ID: Long = -1L
    const val NAME: String = "이름 없음"
    const val MONG_CODE: String = "CH444"
    const val WEIGHT: Double = 0.0
    const val HEALTHY: Double = 0.0
    const val SATIETY: Double = 0.0
    const val STRENGTH: Double = 0.0
    const val SLEEP: Double = 0.0
    const val POOP_COUNT: Int = 0
    const val IS_SLEEPING: Boolean = false
    const val EXP: Double = 0.0
    val STATE_CODE: StateCode = StateCode.EMPTY
    val SHIFT_CODE: ShiftCode = ShiftCode.EMPTY
    const val PAY_POINT: Int = 0
    val BORN: LocalDateTime = LocalDateTime.now()

    const val IS_HAPPY: Boolean = false
    const val IS_EATING: Boolean = false
    const val IS_SELECTED: Boolean = false
    const val IS_GRADUATION_CHECK: Boolean = false

    val SLOT_VO: SlotVo = SlotVo(
        mongId = MONG_ID,
        name = NAME,
        mongCode = MONG_CODE,
        weight = WEIGHT,
        healthy = HEALTHY,
        satiety = SATIETY,
        strength = STRENGTH,
        sleep = SLEEP,
        poopCount = POOP_COUNT,
        isSleeping = IS_SLEEPING,
        exp = EXP,
        stateCode = STATE_CODE,
        shiftCode = SHIFT_CODE,
        payPoint = PAY_POINT,
        born = BORN,
        isHappy = IS_HAPPY,
        isEating = IS_EATING,
        isSelected = IS_SELECTED,
        isGraduateCheck = IS_GRADUATION_CHECK,
    )

    val MAP_CODE_VO: MapCodeVo = MapCodeVo("MP000", "기본")
    val MONG_CODE_VO: MongCodeVo = MongCodeVo("CH000", "없음")
}

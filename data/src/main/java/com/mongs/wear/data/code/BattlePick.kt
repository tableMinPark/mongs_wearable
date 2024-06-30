package com.mongs.wear.data.code

import com.mongs.wear.domain.code.BattlePickCode
import com.mongs.wear.domain.code.BattleStateCode
import com.mongs.wear.domain.code.ShiftCode

enum class BattlePick(
    val code: BattlePickCode
) {
    ATTACK(BattlePickCode.ATTACK),
    DEFENCE(BattlePickCode.DEFENCE),
    HEAL(BattlePickCode.HEAL),
}
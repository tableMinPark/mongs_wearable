package com.mongs.wear.data.code

import com.mongs.wear.domain.code.BattleStateCode

enum class BattleState(
    val code: BattleStateCode
) {
    NONE(BattleStateCode.NONE),
    DEFENCE(BattleStateCode.DEFENCE),
    DAMAGE(BattleStateCode.DAMAGE),
    DAMAGE_AND_HEAL(BattleStateCode.DAMAGE_AND_HEAL),
    HEAL(BattleStateCode.HEAL),
}
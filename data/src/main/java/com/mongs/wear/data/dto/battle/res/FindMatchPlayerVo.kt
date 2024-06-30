package com.mongs.wear.data.dto.battle.res

import com.mongs.wear.data.code.BattleState

data class FindMatchPlayerVo(
    val playerId: String,
    val mongCode: String,
    val hp: Double = 0.0,
    val state: BattleState = BattleState.NONE,
)

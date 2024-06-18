package com.mongs.wear.domain.vo

import com.mongs.wear.domain.code.BattleStateCode

data class MatchPlayerVo(
    val playerId: String = "",
    val mongCode: String = "CH444",
    val hp: Double = 500.0,
    val state: BattleStateCode = BattleStateCode.NONE,
)

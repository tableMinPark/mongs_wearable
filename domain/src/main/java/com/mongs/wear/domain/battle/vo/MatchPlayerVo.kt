package com.mongs.wear.domain.battle.vo

import com.mongs.wear.core.enums.MatchRoundCode

data class MatchPlayerVo(

    val playerId: String = "",

    val mongTypeCode: String = "",

    val hp: Double = 0.0,

    val roundCode: MatchRoundCode = MatchRoundCode.NONE,

    val isWinner: Boolean = false,
)

package com.mongs.wear.domain.vo

import com.mongs.wear.core.enums.MatchRoundCode

data class MatchPlayerVo(

    val playerId: String = "",

    val mongCode: String = "",

    val hp: Double = 0.0,

    val state: MatchRoundCode = MatchRoundCode.NONE,

    val isWinner: Boolean = false,
)

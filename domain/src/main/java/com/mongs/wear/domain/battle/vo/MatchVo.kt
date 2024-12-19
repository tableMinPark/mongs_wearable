package com.mongs.wear.domain.battle.vo

import com.mongs.wear.core.enums.MatchStateCode

data class MatchVo(

    val roomId: Long = 0,

    val round: Int = 0,

    val isLastRound: Boolean = false,

    val stateCode: MatchStateCode = MatchStateCode.NONE,
)

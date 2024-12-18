package com.mongs.wear.domain.battle.vo

import com.mongs.wear.domain.code.MatchStateCode

data class MatchVo(

    val roomId: Long = 0,

    val round: Int = 0,

    val isMatchOver: Boolean = false,

    val matchStateCode: MatchStateCode = MatchStateCode.NONE,
)

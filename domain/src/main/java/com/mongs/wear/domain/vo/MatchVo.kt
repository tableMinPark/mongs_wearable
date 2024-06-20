package com.mongs.wear.domain.vo

import com.mongs.wear.domain.code.MatchStateCode

data class MatchVo(
    val roomId: String = "",
    val round: Int = 0,
    val isMatchOver: Boolean = false,
    val matchStateCode: MatchStateCode = MatchStateCode.NONE,
)

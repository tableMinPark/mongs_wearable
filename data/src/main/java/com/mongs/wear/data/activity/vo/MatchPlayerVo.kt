package com.mongs.wear.data.activity.vo

import com.mongs.wear.core.enums.MatchRoundCode

data class MatchPlayerVo(

    val playerId: String,

    val deviceId: String,

    val mongTypeCode: String,

    val hp: Double,

    val roundCode: MatchRoundCode
)

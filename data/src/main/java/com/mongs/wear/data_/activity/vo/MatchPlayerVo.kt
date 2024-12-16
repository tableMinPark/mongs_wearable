package com.mongs.wear.data_.activity.vo

import com.mongs.wear.data_.activity.enums.MatchRoundCode

data class MatchPlayerVo(

    val playerId: String,

    val deviceId: String,

    val mongTypeCode: String,

    val hp: Double,

    val roundCode: MatchRoundCode
)

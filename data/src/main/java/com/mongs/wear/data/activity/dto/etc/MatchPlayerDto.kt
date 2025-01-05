package com.mongs.wear.data.activity.dto.etc

import com.mongs.wear.core.enums.MatchRoundCode

data class MatchPlayerDto(

    val playerId: String,

    val deviceId: String,

    val mongTypeCode: String,

    val hp: Double,

    val roundCode: MatchRoundCode
)

package com.mongs.wear.data.activity.dto.request

import com.mongs.wear.core.enums.MatchRoundCode

data class PickBattleRequestDto(

    val playerId: String,

    val targetPlayerId: String,

    val pickCode: MatchRoundCode,
)

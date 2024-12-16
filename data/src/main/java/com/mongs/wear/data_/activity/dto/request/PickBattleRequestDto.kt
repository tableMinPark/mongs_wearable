package com.mongs.wear.data_.activity.dto.request

import com.mongs.wear.data_.activity.enums.MatchRoundCode

data class PickBattleRequestDto(

    val playerId: String,

    val targetPlayerId: String,

    val pickCode: MatchRoundCode,
)

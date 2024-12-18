package com.mongs.wear.domain.battle.model

import com.mongs.wear.core.enums.MatchRoundCode

data class MatchPlayerModel(

    val playerId: String,

    val mongTypeCode: String,

    val hp: Double,

    val roundCode: MatchRoundCode,

    val isMe: Boolean,

    val isWin: Boolean,
)

package com.mongs.wear.data.dto.mqttBattle.req

import com.mongs.wear.data.code.BattlePick

data class MatchPickVo(
    val roomId: String,
    val playerId: String,
    val targetPlayerId: String,
    val pick: BattlePick,
)

package com.mongs.wear.data.dto.mqttBattle.res

data class MatchVo(
    val roomId: String,
    val round: Int,
    val matchPlayerVoList: List<MatchPlayerVo>,
)
package com.mongs.wear.data.dto.mqttBattle.res

data class MatchOverVo(
    val roomId: String,
    val round: Int,
    val matchPlayerVoList: List<MatchPlayerVo>,
    val winPlayer: MatchPlayerVo,
)

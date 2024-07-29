package com.mongs.wear.data.dto.battle.res

data class FindMatchResDto(
    val roomId: String,
    val round: Int,
    val matchPlayerVoList: List<FindMatchPlayerVo>,
)
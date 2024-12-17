package com.mongs.wear.data.activity.dto.response

import com.mongs.wear.data.activity.vo.MatchPlayerVo

data class CreateBattleResponseDto(

    val roomId: Long,

    val battlePlayers: Set<MatchPlayerVo>,
)

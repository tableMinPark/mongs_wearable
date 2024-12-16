package com.mongs.wear.data_.activity.dto.response

import com.mongs.wear.data_.activity.vo.MatchPlayerVo

data class CreateBattleResponseDto(

    val roomId: Long,

    val battlePlayers: Set<MatchPlayerVo>,
)

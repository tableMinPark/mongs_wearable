package com.mongs.wear.data.activity.dto.response

import com.mongs.wear.data.activity.dto.etc.MatchPlayerDto

data class CreateBattleResponseDto(

    val roomId: Long,

    val battlePlayers: Set<MatchPlayerDto>,
)

package com.mongs.wear.data.activity.dto.response

import com.mongs.wear.data.activity.vo.MatchPlayerVo

data class FightBattleResponseDto(

    val roomId: Long,

    val round: Int,

    val isLastRound: Boolean,

    val battlePlayers: Set<MatchPlayerVo>,
)

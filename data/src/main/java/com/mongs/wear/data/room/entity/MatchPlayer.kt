package com.mongs.wear.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mongs.wear.data.code.BattleState

@Entity(tableName = "match_player")
data class MatchPlayer(
    @PrimaryKey
    val playerId: String = "",
    val mongCode: String = "",
    val hp: Double = 0.0,
    val state: BattleState = BattleState.NONE,
    val roomId: String = "",
    val isMe: Boolean = false,
    val isWinner: Boolean = false,
)

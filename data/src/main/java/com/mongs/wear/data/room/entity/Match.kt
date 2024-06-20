package com.mongs.wear.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mongs.wear.data.code.MatchState
import com.mongs.wear.domain.code.MatchStateCode
import java.time.LocalDateTime

@Entity(tableName = "match")
data class Match(
    @PrimaryKey
    val roomId: String = "",
    val round: Int = 0,
    val myPlayerId: String = "",
    val isMatchOver: Boolean = false,
    val matchState: MatchState,
    val createdAt: LocalDateTime = LocalDateTime.now(),
)

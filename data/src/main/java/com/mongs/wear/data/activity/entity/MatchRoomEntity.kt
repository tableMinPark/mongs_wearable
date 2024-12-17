package com.mongs.wear.data.activity.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity("mongs_match_room")
data class MatchRoomEntity(

    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),

    val roomId: Long,

    val round: Int,

    val isLastRound: Boolean,

    val isMatching: Boolean,
)

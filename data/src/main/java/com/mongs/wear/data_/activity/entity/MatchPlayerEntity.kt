package com.mongs.wear.data_.activity.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mongs.wear.data_.activity.enums.MatchRoundCode

@Entity("mongs_match_player")
data class MatchPlayerEntity(

    @PrimaryKey
    val playerId: String,

    val deviceId: String,

    val roomId: Long,

    val mongTypeCode: String,

    val hp: Double,

    val roundCode: MatchRoundCode,

    val isMe: Boolean,
)

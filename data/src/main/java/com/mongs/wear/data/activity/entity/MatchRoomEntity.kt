package com.mongs.wear.data.activity.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mongs.wear.core.enums.MatchStateCode
import java.util.UUID

@Entity("mongs_match_room")
data class MatchRoomEntity(

    @PrimaryKey
    val deviceId: String,

    var roomId: Long?,

    var round: Int,

    var isLastRound: Boolean,

    var stateCode: MatchStateCode,

) {

    fun update(
        roomId: Long? = this.roomId,
        round: Int = this.round,
        isLastRound: Boolean = this.isLastRound,
        stateCode: MatchStateCode = this.stateCode,
    ) : MatchRoomEntity {

        this.roomId = roomId
        this.round = round
        this.isLastRound = isLastRound
        this.stateCode = stateCode

        return this
    }
}

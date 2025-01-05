package com.mongs.wear.data.activity.exception

import com.mongs.wear.core.enums.MatchRoundCode
import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.DataErrorCode

class PickMatchException(roomId: Long, playerId: String, pickCode: MatchRoundCode) : ErrorException(
    code = DataErrorCode.DATA_ACTIVITY_BATTLE_PICK_MATCH,
    result = mapOf("roomId" to roomId, "playerId" to playerId, "pickCode" to pickCode),
)
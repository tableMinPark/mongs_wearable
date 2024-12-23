package com.mongs.wear.data.activity.exception

import com.mongs.wear.core.enums.MatchRoundCode
import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.ActivityErrorCode

class PickMatchException(roomId: Long, playerId: String, pickCode: MatchRoundCode) : ErrorException(
    code = ActivityErrorCode.DATA_ACTIVITY_PICK_MATCH,
    result = mapOf("roomId" to roomId, "playerId" to playerId, "pickCode" to pickCode),
)
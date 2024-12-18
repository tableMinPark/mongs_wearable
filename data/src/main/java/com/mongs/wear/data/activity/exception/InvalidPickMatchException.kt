package com.mongs.wear.data.activity.exception

import com.mongs.wear.core.enums.MatchRoundCode
import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.data.auth.enums.DataActivityErrorCode

class InvalidPickMatchException(roomId: Long, playerId: String, pickCode: MatchRoundCode) : ErrorException(
    message = DataActivityErrorCode.ACTIVITY_PICK_MATCH_FAIL.getMessage(),
    result = mapOf("roomId" to roomId, "playerId" to playerId, "pickCode" to pickCode),
)
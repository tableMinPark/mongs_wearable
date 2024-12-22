package com.mongs.wear.data.activity.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.ActivityErrorCode

class EnterMatchException(roomId: Long, playerId: String) : ErrorException(
    code = ActivityErrorCode.ACTIVITY_ENTER_MATCH,
    result = mapOf("roomId" to roomId, "playerId" to playerId),
)
package com.mongs.wear.data.activity.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.ActivityErrorCode

class ExitMatchException(roomId: Long, playerId: String) : ErrorException(
    code = ActivityErrorCode.ACTIVITY_EXIT_MATCH,
    result = mapOf("roomId" to roomId, "playerId" to playerId),
)
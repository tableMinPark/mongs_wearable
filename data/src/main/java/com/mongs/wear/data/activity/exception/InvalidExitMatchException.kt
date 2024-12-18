package com.mongs.wear.data.activity.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.data.auth.enums.DataActivityErrorCode

class InvalidExitMatchException(roomId: Long, playerId: String) : ErrorException(
    message = DataActivityErrorCode.ACTIVITY_EXIT_MATCH_FAIL.getMessage(),
    result = mapOf("roomId" to roomId, "playerId" to playerId),
)
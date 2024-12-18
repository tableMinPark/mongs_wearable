package com.mongs.wear.data.activity.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.data.auth.enums.DataActivityErrorCode

class InvalidEnterMatchException(roomId: Long, playerId: String) : ErrorException(
    message = DataActivityErrorCode.ACTIVITY_ENTER_MATCH_FAIL.getMessage(),
    result = mapOf("roomId" to roomId, "playerId" to playerId),
)
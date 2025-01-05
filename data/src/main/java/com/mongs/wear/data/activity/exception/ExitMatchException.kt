package com.mongs.wear.data.activity.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.DataErrorCode

class ExitMatchException(roomId: Long, playerId: String) : ErrorException(
    code = DataErrorCode.DATA_ACTIVITY_BATTLE_EXIT_MATCH,
    result = mapOf("roomId" to roomId, "playerId" to playerId),
)
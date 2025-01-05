package com.mongs.wear.data.activity.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.DataErrorCode
import java.util.Collections

class UpdateOverMatchException(roomId: Long) : ErrorException(
    code = DataErrorCode.DATA_ACTIVITY_BATTLE_UPDATE_OVER_MATCH,
    result = Collections.singletonMap("roomId", roomId),
)
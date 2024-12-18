package com.mongs.wear.data.activity.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.data.auth.enums.DataActivityErrorCode
import java.util.Collections

class InvalidUpdateOverMatchException(roomId: Long) : ErrorException(
    message = DataActivityErrorCode.ACTIVITY_UPDATE_OVER_MATCH_FAIL.getMessage(),
    result = Collections.singletonMap("roomId", roomId),
)
package com.mongs.wear.data.activity.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.ActivityErrorCode
import java.util.Collections

class NotExistsMatchPlayerException() : ErrorException(
    code = ActivityErrorCode.ACTIVITY_NOT_EXISTS_MATCH_PLAYER,
    result = Collections.emptyMap(),
)
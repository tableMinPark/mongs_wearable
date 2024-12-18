package com.mongs.wear.data.activity.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.data.auth.enums.DataActivityErrorCode
import java.util.Collections

class NotExistsMatchPlayerException() : ErrorException(
    message = DataActivityErrorCode.ACTIVITY_NOT_EXISTS_MATCH_PLAYER.getMessage(),
    result = Collections.emptyMap(),
)
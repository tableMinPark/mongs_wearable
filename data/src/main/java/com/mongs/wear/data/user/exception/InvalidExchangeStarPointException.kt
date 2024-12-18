package com.mongs.wear.data.user.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.data.user.enums.UserErrorCode
import java.util.Collections

class InvalidExchangeStarPointException(mongId: Long) : ErrorException(
    message = UserErrorCode.USER_EXCHANGE_STAR_POINT_FAIL.getMessage(),
    result = Collections.singletonMap("mongId", mongId),
)

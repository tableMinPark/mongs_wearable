package com.mongs.wear.data.user.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.UserErrorCode
import java.util.Collections

class ExchangeStarPointException(mongId: Long) : ErrorException(
    code = UserErrorCode.DATA_USER_EXCHANGE_STAR_POINT,
    result = Collections.singletonMap("mongId", mongId),
)

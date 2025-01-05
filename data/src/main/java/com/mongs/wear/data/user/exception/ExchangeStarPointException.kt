package com.mongs.wear.data.user.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.DataErrorCode
import java.util.Collections

class ExchangeStarPointException(mongId: Long) : ErrorException(
    code = DataErrorCode.DATA_USER_PLAYER_EXCHANGE_STAR_POINT,
    result = Collections.singletonMap("mongId", mongId),
)

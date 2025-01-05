package com.mongs.wear.data.user.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.DataErrorCode
import java.util.Collections

class ExchangeWalkingException : ErrorException(
    code = DataErrorCode.DATA_USER_PLAYER_EXCHANGE_WALKING,
    result = Collections.emptyMap(),
)

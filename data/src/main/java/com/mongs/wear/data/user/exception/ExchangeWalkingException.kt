package com.mongs.wear.data.user.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.UserErrorCode
import java.util.Collections

class ExchangeWalkingException : ErrorException(
    code = UserErrorCode.DATA_USER_EXCHANGE_WALKING,
    result = Collections.emptyMap(),
)

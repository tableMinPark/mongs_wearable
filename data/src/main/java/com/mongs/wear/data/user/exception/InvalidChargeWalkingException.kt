package com.mongs.wear.data.user.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.data.user.enums.UserErrorCode
import java.util.Collections

class InvalidChargeWalkingException : ErrorException(
    message = UserErrorCode.USER_CHARGE_WALKING_FAIL.getMessage(),
    result = Collections.emptyMap(),
)

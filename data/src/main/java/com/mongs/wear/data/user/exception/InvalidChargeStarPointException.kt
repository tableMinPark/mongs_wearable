package com.mongs.wear.data.user.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.data.user.enums.UserErrorCode
import java.util.Collections

class InvalidChargeStarPointException(receipt: String) : ErrorException(
    message = UserErrorCode.USER_CHARGE_STAR_POINT_FAIL.getMessage(),
    result = Collections.singletonMap("receipt", receipt),
)

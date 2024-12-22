package com.mongs.wear.data.user.exception

import com.mongs.wear.core.errors.UserErrorCode
import com.mongs.wear.core.exception.ErrorException
import java.util.Collections

class ChargeStarPointException(receipt: String) : ErrorException(
    code = UserErrorCode.USER_CHARGE_STAR_POINT,
    result = Collections.singletonMap("receipt", receipt),
)

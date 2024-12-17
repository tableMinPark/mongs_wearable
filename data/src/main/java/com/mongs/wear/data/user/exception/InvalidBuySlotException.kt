package com.mongs.wear.data.user.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.data.user.enums.UserErrorCode
import java.util.Collections

class InvalidBuySlotException : ErrorException(
    message = UserErrorCode.USER_BUY_SLOT_FAIL.getMessage(),
    result = Collections.emptyMap(),
)

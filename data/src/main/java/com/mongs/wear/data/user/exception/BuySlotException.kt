package com.mongs.wear.data.user.exception

import com.mongs.wear.core.errors.ErrorCode
import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.UserErrorCode
import java.util.Collections

class BuySlotException(
    code: ErrorCode = UserErrorCode.USER_BUY_SLOT
) : ErrorException(
    code = code,
    result = Collections.emptyMap(),
)

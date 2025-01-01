package com.mongs.wear.data.user.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.UserErrorCode
import java.util.Collections

class GetConsumedOrderIdsException : ErrorException(
    code = UserErrorCode.DATA_USER_CONSUMED_ORDER_IDS,
    result = Collections.emptyMap(),
)

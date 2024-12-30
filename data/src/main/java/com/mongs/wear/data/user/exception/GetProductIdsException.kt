package com.mongs.wear.data.user.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.UserErrorCode
import java.util.Collections

class GetProductIdsException : ErrorException(
    code = UserErrorCode.DATA_USER_GET_PRODUCT_IDS,
    result = Collections.emptyMap(),
)

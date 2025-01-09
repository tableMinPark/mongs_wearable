package com.mongs.wear.data.user.exception

import com.mongs.wear.core.errors.DataErrorCode
import com.mongs.wear.core.exception.ErrorException

class ConsumeProductOrderException(result: Map<String, Any>) : ErrorException(
    code = DataErrorCode.DATA_USER_STORE_CONSUME_PRODUCT_ORDER,
    result = result,
)

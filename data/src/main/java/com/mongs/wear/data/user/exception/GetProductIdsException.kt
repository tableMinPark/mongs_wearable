package com.mongs.wear.data.user.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.DataErrorCode
import java.util.Collections

class GetProductIdsException(result: Map<String, Any>) : ErrorException(
    code = DataErrorCode.DATA_USER_STORE_GET_PRODUCT_IDS,
    result = result,
)

package com.mongs.wear.presentation.common.exception

import com.mongs.wear.core.errors.UserErrorCode
import com.mongs.wear.core.exception.ErrorException
import java.util.Collections

class GetProductException : ErrorException(
    code = UserErrorCode.PRESENTATION_USER_GET_PRODUCTS,
    result = Collections.emptyMap(),
)
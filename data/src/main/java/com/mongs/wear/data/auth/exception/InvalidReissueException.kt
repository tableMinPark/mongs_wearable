package com.mongs.wear.data.auth.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.data.auth.enums.DataAuthErrorCode
import java.util.Collections

class InvalidReissueException : ErrorException(
    message = DataAuthErrorCode.AUTH_REISSUE_FAIL.getMessage(),
    result = Collections.emptyMap(),
)

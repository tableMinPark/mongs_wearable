package com.mongs.wear.data_.auth.exception

import com.mongs.wear.data_.auth.enums.AuthErrorCode
import com.mongs.wear.core.exception.ErrorException
import java.util.Collections

class InvalidReissueException : ErrorException(
    message = AuthErrorCode.AUTH_REISSUE_FAIL.message,
    result = Collections.emptyMap(),
)

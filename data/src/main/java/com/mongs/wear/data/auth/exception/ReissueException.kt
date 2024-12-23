package com.mongs.wear.data.auth.exception

import com.mongs.wear.core.errors.AuthErrorCode
import com.mongs.wear.core.exception.ErrorException
import java.util.Collections

class ReissueException : ErrorException(
    code = AuthErrorCode.DATA_AUTH_REISSUE,
    result = Collections.emptyMap(),
)

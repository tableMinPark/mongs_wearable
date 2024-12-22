package com.mongs.wear.domain.auth.exception

import com.mongs.wear.core.errors.AuthErrorCode
import com.mongs.wear.core.exception.ErrorException
import java.util.Collections

class NotExistsNameException : ErrorException(
    code = AuthErrorCode.AUTH_NOT_EXISTS_NAME,
    result = Collections.emptyMap(),
)

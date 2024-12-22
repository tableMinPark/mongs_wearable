package com.mongs.wear.domain.auth.exception

import com.mongs.wear.core.errors.AuthErrorCode
import com.mongs.wear.core.exception.ErrorException
import java.util.Collections

class NotExistsEmailException : ErrorException(
    code = AuthErrorCode.AUTH_NOT_EXISTS_EMAIL,
    result = Collections.emptyMap(),
)
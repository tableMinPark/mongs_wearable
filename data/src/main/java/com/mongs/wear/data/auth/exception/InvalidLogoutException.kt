package com.mongs.wear.data.auth.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.data.auth.enums.AuthErrorCode
import java.util.Collections

class InvalidLogoutException() : ErrorException(
    message = AuthErrorCode.AUTH_LOGOUT_FAIL.getMessage(),
    result = Collections.emptyMap(),
)

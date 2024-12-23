package com.mongs.wear.data.auth.exception

import com.mongs.wear.core.errors.AuthErrorCode
import com.mongs.wear.core.exception.ErrorException
import java.util.Collections

class LogoutException : ErrorException(
    code = AuthErrorCode.DATA_AUTH_LOGOUT,
    result = Collections.emptyMap(),
)

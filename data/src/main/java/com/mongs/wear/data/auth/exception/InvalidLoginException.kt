package com.mongs.wear.data.auth.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.data.auth.enums.AuthErrorCode
import java.util.Collections

class InvalidLoginException(email: String) : ErrorException(
    message = AuthErrorCode.AUTH_LOGIN_FAIL.getMessage(),
    result = Collections.singletonMap("email", email),
)

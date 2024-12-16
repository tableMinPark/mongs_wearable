package com.mongs.wear.data_.auth.exception

import com.mongs.wear.data_.auth.enums.AuthErrorCode
import com.mongs.wear.core.exception.ErrorException
import java.util.Collections

class InvalidLoginException(email: String) : ErrorException(
    message = AuthErrorCode.AUTH_LOGIN_FAIL.message,
    result = Collections.singletonMap("email", email),
)

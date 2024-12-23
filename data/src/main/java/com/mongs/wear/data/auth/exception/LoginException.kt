package com.mongs.wear.data.auth.exception

import com.mongs.wear.core.errors.AuthErrorCode
import com.mongs.wear.core.exception.ErrorException
import java.util.Collections

class LoginException(email: String) : ErrorException(
    code = AuthErrorCode.DATA_AUTH_LOGIN,
    result = Collections.singletonMap("email", email),
)

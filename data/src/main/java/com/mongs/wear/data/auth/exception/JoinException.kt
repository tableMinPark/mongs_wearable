package com.mongs.wear.data.auth.exception

import com.mongs.wear.core.errors.AuthErrorCode
import com.mongs.wear.core.exception.ErrorException
import java.util.Collections

class JoinException(email: String) : ErrorException(
    code = AuthErrorCode.DATA_AUTH_JOIN,
    result = Collections.singletonMap("email", email),
)

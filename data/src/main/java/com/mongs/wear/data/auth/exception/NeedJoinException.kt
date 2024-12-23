package com.mongs.wear.data.auth.exception

import com.mongs.wear.core.errors.AuthErrorCode
import com.mongs.wear.core.exception.ErrorException
import java.util.Collections

class NeedJoinException(email: String) : ErrorException(
    code = AuthErrorCode.DATA_AUTH_NEED_JOIN,
    result = Collections.singletonMap("email", email),
)

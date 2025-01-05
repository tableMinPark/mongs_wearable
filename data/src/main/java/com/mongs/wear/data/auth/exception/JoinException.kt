package com.mongs.wear.data.auth.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.DataErrorCode
import java.util.Collections

class JoinException(email: String) : ErrorException(
    code = DataErrorCode.DATA_AUTH_JOIN,
    result = Collections.singletonMap("email", email),
)

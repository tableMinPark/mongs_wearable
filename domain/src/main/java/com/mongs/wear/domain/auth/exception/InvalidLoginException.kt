package com.mongs.wear.domain.auth.exception

import com.mongs.wear.core.enums.ErrorCode
import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.auth.enums.DomainAuthErrorCode
import java.util.Collections

class InvalidLoginException(
    code: ErrorCode = DomainAuthErrorCode.DOMAIN_LOGIN_FAIL,
) : ErrorException(
    message = code.getMessage(),
    result = Collections.emptyMap(),
)

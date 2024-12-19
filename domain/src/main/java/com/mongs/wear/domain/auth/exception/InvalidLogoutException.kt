package com.mongs.wear.domain.auth.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.auth.enums.DomainAuthErrorCode
import java.util.Collections

class InvalidLogoutException(
    message: String = DomainAuthErrorCode.DOMAIN_LOGOUT_FAIL.getMessage(),
) : ErrorException(
    message = message,
    result = Collections.emptyMap(),
)

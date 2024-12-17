package com.mongs.wear.domain.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.data.auth.enums.DomainErrorCode
import java.util.Collections

class InvalidLoginException() : ErrorException(
    message = DomainErrorCode.DOMAIN_LOGIN_FAIL.getMessage(),
    result = Collections.emptyMap(),
)

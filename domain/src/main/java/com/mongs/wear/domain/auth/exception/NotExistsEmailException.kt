package com.mongs.wear.domain.auth.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.auth.enums.DomainAuthErrorCode
import java.util.Collections

class NotExistsEmailException : ErrorException(
    message = DomainAuthErrorCode.DOMAIN_NOT_EXISTS_EMAIL.getMessage(),
    result = Collections.emptyMap(),
)

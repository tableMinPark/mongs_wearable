package com.mongs.wear.domain.auth.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.auth.enums.DomainAuthErrorCode
import java.util.Collections

class NotExistsNameException : ErrorException(
    message = DomainAuthErrorCode.DOMAIN_NOT_EXISTS_NAME.getMessage(),
    result = Collections.emptyMap(),
)

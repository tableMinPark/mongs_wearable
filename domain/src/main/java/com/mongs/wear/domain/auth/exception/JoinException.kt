package com.mongs.wear.domain.auth.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode

class JoinException : UseCaseException(
    code = DomainErrorCode.DOMAIN_AUTH_JOIN_FAILED,
)
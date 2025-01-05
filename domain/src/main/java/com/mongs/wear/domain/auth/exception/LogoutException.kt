package com.mongs.wear.domain.auth.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode

class LogoutException : UseCaseException(
    code = DomainErrorCode.DOMAIN_AUTH_LOGOUT_FAILED,
)
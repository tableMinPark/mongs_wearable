package com.mongs.wear.domain.auth.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode

class LoginException : UseCaseException(
    code = DomainErrorCode.DOMAIN_AUTH_LOGIN_FAILED,
)
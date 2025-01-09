package com.mongs.wear.domain.auth.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode
import com.mongs.wear.core.errors.ErrorCode

class LogoutException(
    override val code: ErrorCode = DomainErrorCode.DOMAIN_AUTH_LOGOUT_FAILED,
    override val message: String = code.getMessage()
) : UseCaseException(code = code, message = message)
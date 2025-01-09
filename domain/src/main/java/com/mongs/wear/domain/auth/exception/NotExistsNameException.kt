package com.mongs.wear.domain.auth.exception

import com.mongs.wear.core.errors.DomainErrorCode
import com.mongs.wear.core.errors.ErrorCode
import com.mongs.wear.core.exception.UseCaseException

class NotExistsNameException(
    override val code: ErrorCode = DomainErrorCode.DOMAIN_AUTH_NOT_EXISTS_NAME,
    override val message: String = code.getMessage()
) : UseCaseException(code = code, message = message)

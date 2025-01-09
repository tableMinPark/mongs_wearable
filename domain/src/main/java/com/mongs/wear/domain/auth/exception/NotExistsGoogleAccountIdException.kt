package com.mongs.wear.domain.auth.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode
import com.mongs.wear.core.errors.ErrorCode

class NotExistsGoogleAccountIdException(
    override val code: ErrorCode = DomainErrorCode.DOMAIN_AUTH_NOT_EXISTS_GOOGLE_ACCOUNT_ID,
    override val message: String = code.getMessage()
) : UseCaseException(code = code, message = message)
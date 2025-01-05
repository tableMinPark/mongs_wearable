package com.mongs.wear.domain.auth.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode

class NotExistsGoogleAccountIdException : UseCaseException(
    code = DomainErrorCode.DOMAIN_AUTH_NOT_EXISTS_GOOGLE_ACCOUNT_ID,
)
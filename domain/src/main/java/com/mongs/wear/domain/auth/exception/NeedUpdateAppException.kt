package com.mongs.wear.domain.auth.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode

class NeedUpdateAppException : UseCaseException(
    code = DomainErrorCode.DOMAIN_AUTH_NEED_UPDATE_APP_FAILED,
)
package com.mongs.wear.domain.auth.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode

class NeedJoinException : UseCaseException(
    code = DomainErrorCode.DOMAIN_AUTH_NEED_JOIN_FAILED,
)
package com.mongs.wear.domain.auth.exception

import com.mongs.wear.core.errors.DomainErrorCode
import com.mongs.wear.core.errors.ErrorCode
import com.mongs.wear.core.exception.UseCaseException

class NeedUpdateAppException(
    override val code: ErrorCode = DomainErrorCode.DOMAIN_AUTH_NEED_UPDATE_APP_FAILED,
    override val message: String = code.getMessage()
) : UseCaseException(code = code, message = message)
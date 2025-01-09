package com.mongs.wear.domain.management.exception

import com.mongs.wear.core.errors.DomainErrorCode
import com.mongs.wear.core.errors.ErrorCode
import com.mongs.wear.core.exception.UseCaseException

class CreateMongException(
    override val code: ErrorCode = DomainErrorCode.DOMAIN_MANAGEMENT_CREATE_MONG_FAILED,
    override val message: String = code.getMessage()
) : UseCaseException(code = code, message = message)
package com.mongs.wear.domain.management.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode
import com.mongs.wear.core.errors.ErrorCode

class StrokeMongException(
    val expirationSeconds: Long = 0,
    override val code: ErrorCode = DomainErrorCode.DOMAIN_MANAGEMENT_STROKE_MONG_FAILED,
    override val message: String = code.getMessage().format(expirationSeconds)
) : UseCaseException(code = code, message = message)
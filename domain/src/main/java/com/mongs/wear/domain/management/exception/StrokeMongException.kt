package com.mongs.wear.domain.management.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode

class StrokeMongException : UseCaseException(
    code = DomainErrorCode.DOMAIN_MANAGEMENT_STROKE_MONG_FAILED,
)
package com.mongs.wear.domain.management.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode

class GraduateCheckException : UseCaseException(
    code = DomainErrorCode.DOMAIN_MANAGEMENT_GRADUATE_CHECK_MONG_FAILED,
)
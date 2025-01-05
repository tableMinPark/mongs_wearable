package com.mongs.wear.domain.management.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode

class CreateMongException : UseCaseException(
    code = DomainErrorCode.DOMAIN_MANAGEMENT_CREATE_MONG_FAILED,
)
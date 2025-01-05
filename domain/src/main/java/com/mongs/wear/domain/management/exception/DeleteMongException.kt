package com.mongs.wear.domain.management.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode

class DeleteMongException : UseCaseException(
    code = DomainErrorCode.DOMAIN_MANAGEMENT_DELETE_MONG_FAILED,
)
package com.mongs.wear.domain.management.exception

import com.mongs.wear.core.errors.DomainErrorCode
import com.mongs.wear.core.errors.ErrorCode
import com.mongs.wear.core.exception.UseCaseException

class GetSlotsException(
    override val code: ErrorCode = DomainErrorCode.DOMAIN_MANAGEMENT_GET_SLOTS_FAILED,
    override val message: String = code.getMessage()
) : UseCaseException(code = code, message = message)
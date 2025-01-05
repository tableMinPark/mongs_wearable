package com.mongs.wear.domain.management.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode

class GetCurrentSlotException : UseCaseException(
    code = DomainErrorCode.DOMAIN_MANAGEMENT_GET_CURRENT_SLOT_FAILED,
)
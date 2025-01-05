package com.mongs.wear.domain.management.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode

class SetCurrentSlotException : UseCaseException(
    code = DomainErrorCode.DOMAIN_MANAGEMENT_SET_CURRENT_SLOT_FAILED,
)
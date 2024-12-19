package com.mongs.wear.domain.slot.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.slot.enums.DomainSlotErrorCode
import java.util.Collections

class InvalidGetCurrentSlotException : ErrorException(
    message = DomainSlotErrorCode.DOMAIN_GET_CURRENT_SLOT_FAIL.getMessage(),
    result = Collections.emptyMap(),
)

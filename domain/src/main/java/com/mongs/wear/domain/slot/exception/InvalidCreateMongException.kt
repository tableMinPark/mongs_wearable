package com.mongs.wear.domain.slot.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.slot.enums.DomainSlotErrorCode
import java.util.Collections

class InvalidCreateMongException : ErrorException(
    message = DomainSlotErrorCode.DOMAIN_CREATE_MONG_FAIL.getMessage(),
    result = Collections.emptyMap(),
)

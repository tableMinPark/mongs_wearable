package com.mongs.wear.domain.slot.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.slot.enums.DomainSlotErrorCode
import java.util.Collections

class InvalidStrokeMongException : ErrorException(
    message = DomainSlotErrorCode.DOMAIN_STROKE_MONG_FAIL.getMessage(),
    result = Collections.emptyMap(),
)

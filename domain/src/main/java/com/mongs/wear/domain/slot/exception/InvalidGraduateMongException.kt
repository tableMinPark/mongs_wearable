package com.mongs.wear.domain.slot.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.slot.enums.DomainSlotErrorCode
import java.util.Collections

class InvalidGraduateMongException : ErrorException(
    message = DomainSlotErrorCode.DOMAIN_GRADUATE_MONG_FAIL.getMessage(),
    result = Collections.emptyMap(),
)

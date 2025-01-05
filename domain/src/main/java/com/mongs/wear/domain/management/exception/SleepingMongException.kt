package com.mongs.wear.domain.management.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode

class SleepingMongException : UseCaseException(
    code = DomainErrorCode.DOMAIN_MANAGEMENT_SLEEP_MONG_FAILED,
)
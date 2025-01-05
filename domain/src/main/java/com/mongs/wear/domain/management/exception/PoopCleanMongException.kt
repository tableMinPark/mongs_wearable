package com.mongs.wear.domain.management.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode

class PoopCleanMongException : UseCaseException(
    code = DomainErrorCode.DOMAIN_MANAGEMENT_POOP_CLEAN_MONG_FAILED,
)
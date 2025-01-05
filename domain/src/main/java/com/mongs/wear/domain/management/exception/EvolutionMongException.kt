package com.mongs.wear.domain.management.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode

class EvolutionMongException : UseCaseException(
    code = DomainErrorCode.DOMAIN_MANAGEMENT_EVOLUTION_MONG_FAILED,
)
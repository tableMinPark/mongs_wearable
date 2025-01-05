package com.mongs.wear.domain.player.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode

class GetStepsException : UseCaseException(
    code = DomainErrorCode.DOMAIN_GET_STEPS_FAILED,
)
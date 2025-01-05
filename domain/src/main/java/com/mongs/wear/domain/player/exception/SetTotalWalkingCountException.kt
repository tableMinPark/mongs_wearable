package com.mongs.wear.domain.player.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode

class SetTotalWalkingCountException : UseCaseException(
    code = DomainErrorCode.DOMAIN_SET_TOTAL_WALKING_COUNT_FAILED,
)
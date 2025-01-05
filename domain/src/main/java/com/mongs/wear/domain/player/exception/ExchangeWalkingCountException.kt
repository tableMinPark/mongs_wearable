package com.mongs.wear.domain.player.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode

class ExchangeWalkingCountException : UseCaseException(
    code = DomainErrorCode.DOMAIN_PLAYER_EXCHANGE_WALKING_COUNT_FAILED,
)
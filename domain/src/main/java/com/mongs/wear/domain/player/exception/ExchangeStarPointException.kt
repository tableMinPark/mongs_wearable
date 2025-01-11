package com.mongs.wear.domain.player.exception

import com.mongs.wear.core.errors.DomainErrorCode
import com.mongs.wear.core.errors.ErrorCode
import com.mongs.wear.core.exception.UseCaseException

class ExchangeStarPointException(
    override val code: ErrorCode = DomainErrorCode.DOMAIN_PLAYER_EXCHANGE_STAR_POINT_FAILED,
    override val message: String = code.getMessage()
) : UseCaseException(code = code, message = message)
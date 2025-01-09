package com.mongs.wear.domain.player.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode
import com.mongs.wear.core.errors.ErrorCode

class BuySlotException(
    override val code: ErrorCode = DomainErrorCode.DOMAIN_PLAYER_BUY_SLOT_FAILED,
    override val message: String = code.getMessage()
) : UseCaseException(code = code, message = message)
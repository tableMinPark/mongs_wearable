package com.mongs.wear.domain.player.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode

class BuySlotException : UseCaseException(
    code = DomainErrorCode.DOMAIN_PLAYER_BUY_SLOT_FAILED,
)
package com.mongs.wear.domain.player.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode

class GetSlotCountException : UseCaseException(
    code = DomainErrorCode.DOMAIN_PLAYER_GET_SLOT_COUNT_FAILED,
)
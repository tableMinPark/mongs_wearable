package com.mongs.wear.domain.player.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode
import com.mongs.wear.core.errors.ErrorCode

class GetSlotCountException(
    override val code: ErrorCode = DomainErrorCode.DOMAIN_PLAYER_GET_SLOT_COUNT_FAILED,
    override val message: String = code.getMessage()
) : UseCaseException(code = code, message = message)
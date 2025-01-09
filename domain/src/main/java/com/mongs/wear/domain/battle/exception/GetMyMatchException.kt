package com.mongs.wear.domain.battle.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode
import com.mongs.wear.core.errors.ErrorCode

class GetMyMatchException(
    override val code: ErrorCode = DomainErrorCode.DOMAIN_BATTLE_GET_MY_MATCH_PLAYER_FAILED,
    override val message: String = code.getMessage()
) : UseCaseException(code = code, message = message)
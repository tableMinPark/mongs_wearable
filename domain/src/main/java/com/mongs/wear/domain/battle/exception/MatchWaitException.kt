package com.mongs.wear.domain.battle.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode

class MatchWaitException : UseCaseException(
    code = DomainErrorCode.DOMAIN_BATTLE_MATCH_WAIT_FAILED,
)
package com.mongs.wear.domain.battle.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode

class MatchStartException : UseCaseException(
    code = DomainErrorCode.DOMAIN_BATTLE_MATCH_START_FAILED,
)
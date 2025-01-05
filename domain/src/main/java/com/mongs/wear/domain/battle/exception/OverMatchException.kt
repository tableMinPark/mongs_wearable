package com.mongs.wear.domain.battle.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode

class OverMatchException : UseCaseException(
    code = DomainErrorCode.DOMAIN_BATTLE_OVER_MATCH_FAILED,
)
package com.mongs.wear.domain.battle.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode

class GetMatchException : UseCaseException(
    code = DomainErrorCode.DOMAIN_BATTLE_GET_MATCH_FAILED,
)
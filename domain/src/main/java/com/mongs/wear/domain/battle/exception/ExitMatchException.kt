package com.mongs.wear.domain.battle.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode

class ExitMatchException : UseCaseException(
    code = DomainErrorCode.DOMAIN_BATTLE_EXIT_MATCH_FAILED,
)
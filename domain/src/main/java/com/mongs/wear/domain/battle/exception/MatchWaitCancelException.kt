package com.mongs.wear.domain.battle.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode

class MatchWaitCancelException : UseCaseException(
    code = DomainErrorCode.DOMAIN_BATTLE_MATCH_WAIT_CANCEL_FAILED,
)
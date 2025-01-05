package com.mongs.wear.domain.battle.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode

class PickMatchException : UseCaseException(
    code = DomainErrorCode.DOMAIN_BATTLE_PICK_MATCH_FAILED,
)
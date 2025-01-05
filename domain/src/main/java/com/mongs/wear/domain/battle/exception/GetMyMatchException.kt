package com.mongs.wear.domain.battle.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode

class GetMyMatchException : UseCaseException(
    code = DomainErrorCode.DOMAIN_BATTLE_GET_MY_MATCH_PLAYER_FAILED,
)
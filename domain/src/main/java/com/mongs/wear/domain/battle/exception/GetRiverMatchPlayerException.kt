package com.mongs.wear.domain.battle.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode

class GetRiverMatchPlayerException : UseCaseException(
    code = DomainErrorCode.DOMAIN_BATTLE_GET_RIVER_MATCH_PLAYER_FAILED,
)
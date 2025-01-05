package com.mongs.wear.domain.player.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode

class GetStarPointException : UseCaseException(
    code = DomainErrorCode.DOMAIN_PLAYER_GET_STAR_POINT_FAILED,
)
package com.mongs.wear.domain.device.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode

class GetBackgroundMapCodeException : UseCaseException(
    code = DomainErrorCode.DOMAIN_DEVICE_GET_BACKGROUND_MAP_CODE_FAILED,
)
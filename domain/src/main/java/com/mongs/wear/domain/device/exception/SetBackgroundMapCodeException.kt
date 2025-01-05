package com.mongs.wear.domain.device.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode

class SetBackgroundMapCodeException : UseCaseException(
    code = DomainErrorCode.DOMAIN_DEVICE_SET_BACKGROUND_MAP_CODE_FAILED,
)
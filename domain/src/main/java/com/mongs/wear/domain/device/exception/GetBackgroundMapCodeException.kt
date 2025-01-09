package com.mongs.wear.domain.device.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode
import com.mongs.wear.core.errors.ErrorCode

class GetBackgroundMapCodeException(
    override val code: ErrorCode = DomainErrorCode.DOMAIN_DEVICE_GET_BACKGROUND_MAP_CODE_FAILED,
    override val message: String = code.getMessage()
) : UseCaseException(code = code, message = message)
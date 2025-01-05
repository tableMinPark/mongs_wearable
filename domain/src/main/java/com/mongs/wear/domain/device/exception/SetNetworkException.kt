package com.mongs.wear.domain.device.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode

class SetNetworkException : UseCaseException(
    code = DomainErrorCode.DOMAIN_DEVICE_SET_NETWORK_FAILED,
)
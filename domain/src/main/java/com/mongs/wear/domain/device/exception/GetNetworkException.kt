package com.mongs.wear.domain.device.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode

class GetNetworkException : UseCaseException(
    code = DomainErrorCode.DOMAIN_DEVICE_GET_NETWORK_FAILED,
)
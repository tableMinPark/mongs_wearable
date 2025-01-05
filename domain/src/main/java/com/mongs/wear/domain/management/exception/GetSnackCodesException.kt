package com.mongs.wear.domain.management.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode

class GetSnackCodesException : UseCaseException(
    code = DomainErrorCode.DOMAIN_MANAGEMENT_SNACK_CODES_FAILED,
)
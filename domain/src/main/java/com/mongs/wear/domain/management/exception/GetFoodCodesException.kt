package com.mongs.wear.domain.management.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode
import com.mongs.wear.core.errors.ErrorCode

class GetFoodCodesException(
    override val code: ErrorCode = DomainErrorCode.DOMAIN_MANAGEMENT_GET_FOOD_CODES_FAILED,
    override val message: String = code.getMessage()
) : UseCaseException(code = code, message = message)
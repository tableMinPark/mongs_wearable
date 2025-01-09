package com.mongs.wear.domain.store.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode
import com.mongs.wear.core.errors.ErrorCode

class GetProductIdsException(
    override val code: ErrorCode = DomainErrorCode.DOMAIN_GET_PRODUCT_IDS_FAILED,
    override val message: String = code.getMessage()
) : UseCaseException(code = code, message = message)
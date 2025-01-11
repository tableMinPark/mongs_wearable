package com.mongs.wear.domain.store.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode
import com.mongs.wear.core.errors.ErrorCode

class ConsumeProductOrderException(
    override val code: ErrorCode = DomainErrorCode.DOMAIN_STORE_CONSUME_PRODUCT_ORDER_FAILED,
    override val message: String = code.getMessage()
) : UseCaseException(
    code = DomainErrorCode.DOMAIN_STORE_CONSUME_PRODUCT_ORDER_FAILED,
)
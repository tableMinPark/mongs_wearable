package com.mongs.wear.domain.store.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode

class ConsumeProductOrderException : UseCaseException(
    code = DomainErrorCode.DOMAIN_CONSUME_PRODUCT_ORDER_FAILED,
)
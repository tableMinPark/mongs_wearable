package com.mongs.wear.domain.store.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode

class GetProductIdsException : UseCaseException(
    code = DomainErrorCode.DOMAIN_GET_PRODUCT_IDS_FAILED,
)
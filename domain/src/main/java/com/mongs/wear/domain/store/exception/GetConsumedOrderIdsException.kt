package com.mongs.wear.domain.store.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode

class GetConsumedOrderIdsException : UseCaseException(
    code = DomainErrorCode.DOMAIN_GET_CONSUMED_ORDER_IDS_FAILED,
)
package com.mongs.wear.domain.collection.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode
import com.mongs.wear.core.errors.ErrorCode

class GetMongCollectionException(
    override val code: ErrorCode = DomainErrorCode.DOMAIN_COLLECTION_GET_MONG_COLLECTIONS_FAILED,
    override val message: String = code.getMessage()
) : UseCaseException(code = code, message = message)
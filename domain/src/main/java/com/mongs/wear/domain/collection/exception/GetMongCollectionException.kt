package com.mongs.wear.domain.collection.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode

class GetMongCollectionException : UseCaseException(
    code = DomainErrorCode.DOMAIN_COLLECTION_GET_MONG_COLLECTIONS_FAILED,
)
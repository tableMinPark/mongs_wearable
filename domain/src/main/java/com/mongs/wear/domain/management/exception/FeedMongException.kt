package com.mongs.wear.domain.management.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode

class FeedMongException : UseCaseException(
    code = DomainErrorCode.DOMAIN_MANAGEMENT_FEED_MONG_FAILED,
)
package com.mongs.wear.domain.management.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode

class TrainingMongException : UseCaseException(
    code = DomainErrorCode.DOMAIN_MANAGEMENT_TRAINING_MONG_FAILED,
)
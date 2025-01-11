package com.mongs.wear.domain.training.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode
import com.mongs.wear.core.errors.ErrorCode

class TrainingMongException(
    override val code: ErrorCode = DomainErrorCode.DOMAIN_TRAINING_RUNNER_FAILED,
    override val message: String = code.getMessage()
) : UseCaseException(code = code, message = message)
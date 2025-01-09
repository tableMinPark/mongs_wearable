package com.mongs.wear.domain.feedback.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode
import com.mongs.wear.core.errors.ErrorCode

class CreateFeedbackException(
    override val code: ErrorCode = DomainErrorCode.DOMAIN_FEEDBACK_CREATE_FEEDBACK_FAILED,
    override val message: String = code.getMessage()
) : UseCaseException(code = code, message = message)
package com.mongs.wear.domain.feedback.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode

class CreateFeedbackException : UseCaseException(
    code = DomainErrorCode.DOMAIN_FEEDBACK_CREATE_FEEDBACK_FAILED,
)
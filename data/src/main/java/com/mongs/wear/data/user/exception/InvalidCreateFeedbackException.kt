package com.mongs.wear.data.user.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.data.user.enums.UserErrorCode
import java.util.Collections

class InvalidCreateFeedbackException : ErrorException(
    message = UserErrorCode.USER_CREATE_FEEDBACK_FAIL.getMessage(),
    result = Collections.emptyMap(),
)

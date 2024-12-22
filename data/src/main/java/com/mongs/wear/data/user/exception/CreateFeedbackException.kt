package com.mongs.wear.data.user.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.UserErrorCode
import java.util.Collections

class CreateFeedbackException : ErrorException(
    code = UserErrorCode.USER_CREATE_FEEDBACK,
    result = Collections.emptyMap(),
)

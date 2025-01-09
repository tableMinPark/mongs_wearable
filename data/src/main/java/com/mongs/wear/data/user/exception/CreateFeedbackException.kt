package com.mongs.wear.data.user.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.DataErrorCode
import java.util.Collections

class CreateFeedbackException(result: Map<String, Any>) : ErrorException(
    code = DataErrorCode.DATA_USER_FEEDBACK_CREATE_FEEDBACK,
    result = result,
)

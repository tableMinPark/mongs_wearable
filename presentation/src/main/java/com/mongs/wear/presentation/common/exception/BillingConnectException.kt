package com.mongs.wear.presentation.common.exception

import com.mongs.wear.core.errors.UserErrorCode
import com.mongs.wear.core.exception.ErrorException
import java.util.Collections

class BillingConnectException : ErrorException(
    code = UserErrorCode.PRESENTATION_USER_BILLING_CONNECT,
    result = Collections.emptyMap(),
)
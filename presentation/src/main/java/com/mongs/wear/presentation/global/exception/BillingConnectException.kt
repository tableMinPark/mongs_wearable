package com.mongs.wear.presentation.global.exception

import com.mongs.wear.core.exception.PresentationException
import com.mongs.wear.core.errors.PresentationErrorCode

class BillingConnectException : PresentationException (
    code = PresentationErrorCode.PRESENTATION_USER_BILLING_CONNECT,
)
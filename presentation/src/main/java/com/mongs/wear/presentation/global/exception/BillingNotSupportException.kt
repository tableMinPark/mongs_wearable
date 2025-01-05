package com.mongs.wear.presentation.global.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.PresentationErrorCode

class BillingNotSupportException : ErrorException(
    code = PresentationErrorCode.PRESENTATION_USER_BILLING_NOT_SUPPORT,
)
package com.mongs.wear.presentation.global.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.PresentationErrorCode

class GetProductException : ErrorException(
    code = PresentationErrorCode.PRESENTATION_USER_GET_PRODUCTS,
)
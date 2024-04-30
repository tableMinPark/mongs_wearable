package com.paymong.wear.data.api.notification.dto.response

import com.paymong.wear.data.api.notification.code.PublishCode

data class BasicPublish<T>(
    val code: PublishCode,
    val data: T
)

package com.paymong.wear.data.dto.mqtt.res

import com.paymong.wear.data.api.code.PublishCode

data class BasicPublish<T>(
    val code: PublishCode,
    val data: T
)

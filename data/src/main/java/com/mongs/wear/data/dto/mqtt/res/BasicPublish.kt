package com.mongs.wear.data.dto.mqtt.res

import com.mongs.wear.data.api.code.PublishCode

data class BasicPublish<T>(
    val code: PublishCode,
    val data: T
)

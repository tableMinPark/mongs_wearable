package com.mongs.wear.data.dto.mqttEvent

import com.mongs.wear.data.api.code.PublishEventCode
import java.time.LocalDateTime

data class BasicEventPublish<T>(
    val code: PublishEventCode,
    val data: T,
    val createdAt: LocalDateTime = LocalDateTime.now(),
)

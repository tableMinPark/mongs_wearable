package com.mongs.wear.data.dto.mqttBattle

import com.mongs.wear.data.api.code.PublishBattleCode
import java.time.LocalDateTime

data class BasicBattlePublish<T>(
    val code: PublishBattleCode,
    val data: T,
    val createdAt: LocalDateTime = LocalDateTime.now(),
)

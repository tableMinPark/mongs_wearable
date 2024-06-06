package com.mongs.wear.data.dto.mqtt.res

data class MongStatusVo(
    val mongId: Long,
    val weight: Double,
    val strengthPercent: Double,
    val satietyPercent: Double,
    val healthyPercent: Double,
    val sleepPercent: Double,
)
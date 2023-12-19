package com.paymong.wear.domain.dto.room

import java.time.LocalDateTime

data class MongInfo(
    val mongName: String = "이름 없음",
    val born: LocalDateTime = LocalDateTime.now(),
    val weight: Int = 0
)

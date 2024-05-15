package com.paymong.wear.data.dto.common.res

import java.time.LocalDateTime

data class FindVersionResDto(
    val newestBuildVersion: String,
    val createdAt: LocalDateTime,
    val updateApp: Boolean,
    val updateCode: Boolean,
)

package com.paymong.wear.data.api.common.dto.response

import java.time.LocalDateTime

data class FindVersionResDto(
    val newestBuildVersion: String,
    val createdAt: LocalDateTime,
    val mustUpdateApp: Boolean,
    val mustUpdateCode: Boolean,
)

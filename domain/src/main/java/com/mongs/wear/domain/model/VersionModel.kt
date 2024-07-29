package com.mongs.wear.domain.model

import java.time.LocalDateTime

data class VersionModel(
    val newestBuildVersion: String,
    val createdAt: LocalDateTime,
    val updateApp: Boolean,
    val updateCode: Boolean,
)

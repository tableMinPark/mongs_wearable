package com.mongs.wear.data.dto.management.res

data class FindFeedLogResDto(
    val accountId: Long,
    val mongId: Long,
    val code: String,
    val isCanBuy: Boolean
)
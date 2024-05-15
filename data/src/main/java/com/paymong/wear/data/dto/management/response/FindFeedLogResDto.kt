package com.paymong.wear.data.dto.management.response

data class FindFeedLogResDto(
    val accountId: Long,
    val mongId: Long,
    val code: String,
    val isCanBuy: Boolean
)
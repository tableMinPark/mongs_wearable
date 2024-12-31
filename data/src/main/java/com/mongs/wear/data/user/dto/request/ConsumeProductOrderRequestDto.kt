package com.mongs.wear.data.user.dto.request

data class ConsumeProductOrderRequestDto(

    val productId: String,

    val orderId: String,

    val purchaseToken: String,
)

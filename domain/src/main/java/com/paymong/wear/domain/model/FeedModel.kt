package com.paymong.wear.domain.model

import java.time.LocalDateTime

data class FeedModel(
    var name: String = "",
    var foodCode: String = "",
    var price: Int = 0,
    var buyRegDt: LocalDateTime = LocalDateTime.now()
)

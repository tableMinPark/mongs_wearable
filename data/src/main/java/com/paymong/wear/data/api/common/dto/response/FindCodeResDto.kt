package com.paymong.wear.data.api.common.dto.response

import com.paymong.wear.data.room.mapCode.MapCode

data class FindCodeResDto(
    val codeIntegrity: String,
    val mapCodeList: List<FindMapCodeResDto>,
    val mongCodeList: List<FindMongCodeResDto>,
    val foodCodeList: List<FindFoodCodeResDto>,
    val feedbackCodeList: List<FindFeedbackCodeResDto>,
)
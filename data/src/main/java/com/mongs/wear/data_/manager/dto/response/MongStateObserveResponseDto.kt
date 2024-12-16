package com.mongs.wear.data_.manager.dto.response

import com.mongs.wear.data_.manager.enums.MongStateCode

data class MongStateObserveResponseDto(

    val stateCode: MongStateCode,

    val isSleep: Boolean,
)

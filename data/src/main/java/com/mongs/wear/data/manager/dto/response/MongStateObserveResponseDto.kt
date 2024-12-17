package com.mongs.wear.data.manager.dto.response

import com.mongs.wear.core.enums.MongStateCode

data class MongStateObserveResponseDto(

    val stateCode: MongStateCode,

    val isSleep: Boolean,
)

package com.mongs.wear.data.manager.dto.etc

import com.mongs.wear.core.enums.MongStateCode

data class MongStateObserveDto(

    val stateCode: MongStateCode,

    val isSleep: Boolean,
)

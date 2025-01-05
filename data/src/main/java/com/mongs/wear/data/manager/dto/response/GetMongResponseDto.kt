package com.mongs.wear.data.manager.dto.response

data class GetMongResponseDto(

    val mong: MongResponseDto,

    val mongState: MongStateResponseDto,

    val mongStatus: MongStatusResponseDto,
)

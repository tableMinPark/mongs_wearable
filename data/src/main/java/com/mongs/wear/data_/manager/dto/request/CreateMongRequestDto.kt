package com.mongs.wear.data_.manager.dto.request

import java.time.LocalTime

data class CreateMongRequestDto(

    val name: String,

    val sleepAt: LocalTime,

    val wakeupAt: LocalTime,
)

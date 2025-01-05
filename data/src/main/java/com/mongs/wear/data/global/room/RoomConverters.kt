package com.mongs.wear.data.global.room

import androidx.room.TypeConverter
import java.time.LocalDateTime

class RoomConverters {

    @TypeConverter
    fun strToLocalDateTime(str: String): LocalDateTime {
        return str.let { LocalDateTime.parse(it) }
    }

    @TypeConverter
    fun localDateTimeToStr(date: LocalDateTime): String {
        return date.toString()
    }
}
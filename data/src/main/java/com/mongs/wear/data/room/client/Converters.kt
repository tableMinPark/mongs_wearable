package com.mongs.wear.data.room.client

import androidx.room.TypeConverter
import java.time.LocalDateTime

class Converters {
    @TypeConverter
    fun strToLocalDateTime(str: String): LocalDateTime {
        return str.let { LocalDateTime.parse(it) }
    }

    @TypeConverter
    fun localDateTimeToStr(date: LocalDateTime): String {
        return date.toString()
    }
}
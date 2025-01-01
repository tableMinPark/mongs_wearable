package com.mongs.wear.core.adapter

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class GsonLocalDateTimeFormatAdapter : JsonSerializer<LocalDateTime?>, JsonDeserializer<LocalDateTime?> {

    @Synchronized
    override fun serialize(localDateTime: LocalDateTime?, type: Type?, jsonSerializationContext: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS").format(localDateTime))
    }

    @Synchronized
    override fun deserialize(jsonElement: JsonElement, type: Type?, jsonDeserializationContext: JsonDeserializationContext?): LocalDateTime {
        return LocalDateTime.parse(jsonElement.asString, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }
}

class GsonLocalTimeAdapter : JsonSerializer<LocalTime?>, JsonDeserializer<LocalTime?> {
    override fun serialize(localTime: LocalTime?, type: Type?, jsonSerializationContext: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(DateTimeFormatter.ofPattern("HH:mm:ss").format(localTime))
    }

    override fun deserialize(jsonElement: JsonElement, type: Type?, jsonDeserializationContext: JsonDeserializationContext?): LocalTime? {
        return LocalTime.parse(jsonElement.asString, DateTimeFormatter.ISO_TIME)
    }
}
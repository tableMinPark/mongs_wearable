package com.mongs.wear.data.global.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mongs.wear.core.dto.response.ResponseDto
import okhttp3.ResponseBody
import java.util.Collections
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HttpUtil @Inject constructor(
    private val gson: Gson,
) {
    fun getErrorResult(errorBody: ResponseBody?) : Map<String, Any> {
        return errorBody?.let { body ->

            val resultJson = gson.toJson(gson.fromJson(body.string(), ResponseDto::class.java).result)

            gson.fromJson(resultJson, object : TypeToken<Map<String, Any>>() {}.type)

        } ?: run {
            Collections.emptyMap()
        }
    }
}
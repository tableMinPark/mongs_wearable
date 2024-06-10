package com.mongs.wear.data.api.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import timber.log.Timber
import java.net.ConnectException

class HttpLogInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        return try {
            val response = chain.proceed(chain.request())
            Timber.tag("HttpLogInterceptor")
                .i("[" + response.code() + "] " + request.method() + " " + request.url())
            response
        } catch (_: ConnectException) {
            Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(500)
                .message("Connected Fail")
                .body(ResponseBody.create(null, ""))
                .build()
        }
    }
}
package com.mongs.wear.data.common.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import java.net.ConnectException

class HttpLogInterceptor : Interceptor {

    companion object {
        private const val CONNECTION_RESPONSE_CODE = 500
        private const val CONNECTION_RESPONSE_MESSAGE = "서버 연결 실패"
    }

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()

        val response = try {
             chain.proceed(chain.request())
        } catch (_: ConnectException) {
            Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(CONNECTION_RESPONSE_CODE)
                .message(CONNECTION_RESPONSE_MESSAGE)
                .body(ResponseBody.create(MediaType.get("application/json"), ""))
                .build()
        }

        Log.i("HttpLogInterceptor", "[${response.code()}] ${request.method()} ${request.url()}")

        return response
    }
}
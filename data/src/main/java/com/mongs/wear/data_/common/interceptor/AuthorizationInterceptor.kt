package com.mongs.wear.data_.common.interceptor

import com.mongs.wear.domain.repositroy.AuthRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Request
import okhttp3.Response

class AuthorizationInterceptor (
    private val authRepository: AuthRepository,
) : Interceptor {

    override fun intercept(chain: Chain): Response {

        val accessToken = runBlocking { return@runBlocking authRepository.getAccessToken() }

        val response = chain.proceed(this.generateRequest(chain, accessToken))

        if (response.code() == 401) {

            val newAccessToken = runBlocking { return@runBlocking authRepository.reissue() }

            return chain.proceed(this.generateRequest(chain, newAccessToken))

        } else {
            return response
        }
    }

    private fun generateRequest(chain: Chain, accessToken: String) : Request {

        return chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .build()
    }
}
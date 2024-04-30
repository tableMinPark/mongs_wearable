package com.paymong.wear.data.api.interceptor

import com.paymong.wear.data.dataStore.token.TokenDataStore
import com.paymong.wear.domain.repository.auth.AuthRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import java.lang.RuntimeException

class AuthorizationInterceptor (
    private val tokenDataStore: TokenDataStore,
    private val authRepository: AuthRepository
) : Interceptor {
    override fun intercept(chain: Chain): Response {
        val accessToken = tokenDataStore.findAccessToken()
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .build()

        val response = chain.proceed(newRequest)

        if (response.code() == 401) {
            response.close()
            return reissueAndRetry(chain)
        }

        return response
    }
    private fun reissueAndRetry(chain: Chain) : Response {
        try {
            runBlocking { authRepository.reissue() }

            val accessToken = tokenDataStore.findAccessToken()
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $accessToken")
                .build()

            return chain.proceed(newRequest)
        } catch (_: RuntimeException) {}

        return chain.proceed(chain.request())
    }
}
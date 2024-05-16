package com.paymong.wear.data.api.interceptor

import com.paymong.wear.domain.repositroy.AuthRepository
import com.paymong.wear.domain.repositroy.DeviceRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import java.lang.RuntimeException

class AuthorizationInterceptor (
    private val deviceRepository: DeviceRepository,
    private val authRepository: AuthRepository
) : Interceptor {
    override fun intercept(chain: Chain): Response {
        val accessToken = runBlocking { return@runBlocking deviceRepository.getAccessToken() }
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
            val accessToken = runBlocking {
                val refreshToken = deviceRepository.getRefreshToken()
                val loginModel = authRepository.reissue(refreshToken = refreshToken)
                val newAccessToken = loginModel.accessToken
                val newRefreshToken = loginModel.refreshToken

                deviceRepository.setAccessToken(accessToken = newAccessToken)
                deviceRepository.setRefreshToken(refreshToken = newRefreshToken)

                return@runBlocking newAccessToken
            }

            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $accessToken")
                .build()

            return chain.proceed(newRequest)
        } catch (_: RuntimeException) {}

        return chain.proceed(chain.request())
    }
}
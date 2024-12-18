package com.mongs.wear.data.common.interceptor

import com.mongs.wear.data.auth.api.AuthApi
import com.mongs.wear.data.auth.dataStore.TokenDataStore
import com.mongs.wear.data.auth.dto.request.ReissueRequestDto
import com.mongs.wear.data.auth.exception.InvalidReissueException
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Request
import okhttp3.Response

class AuthorizationInterceptor (
    private val authApi: AuthApi,
    private val tokenDataStore: TokenDataStore,
) : Interceptor {

    override fun intercept(chain: Chain): Response {

        val accessToken = runBlocking { return@runBlocking tokenDataStore.getAccessToken() }

        val response = chain.proceed(this.generateRequest(chain, accessToken))

        if (response.code() == 401) {

            val newAccessToken = runBlocking { return@runBlocking this@AuthorizationInterceptor.reissue() }

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

    /**
     * 토큰 재발행
     */
    private suspend fun reissue() : String {

        val refreshToken = tokenDataStore.getRefreshToken()

        val response = authApi.reissue(ReissueRequestDto(refreshToken = refreshToken))

        if (response.isSuccessful) {
            response.body()?.let { body ->

                tokenDataStore.setAccessToken(accessToken = body.result.accessToken)
                tokenDataStore.setRefreshToken(refreshToken = body.result.refreshToken)

                return body.result.accessToken
            }
        }

        throw InvalidReissueException()
    }
}
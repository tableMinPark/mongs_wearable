package com.mongs.wear.data_.auth.repository

import com.mongs.wear.data_.auth.api.AuthApi
import com.mongs.wear.data_.auth.dataStore.TokenDataStore
import com.mongs.wear.data_.auth.dto.request.LoginRequestDto
import com.mongs.wear.data_.auth.dto.request.LogoutRequestDto
import com.mongs.wear.data_.auth.exception.InvalidLoginException
import com.mongs.wear.data_.auth.exception.InvalidReissueException
import com.mongs.wear.core.exception.ApiException
import com.mongs.wear.domain.repositroy.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val tokenDataStore: TokenDataStore,
    private val authApi: AuthApi,
) : AuthRepository {

    companion object {
        private const val APP_CODE = "MONGS"
        private const val BUILD_VERSION = "1.0.0"
    }

    /**
     * 로그인
     */
    override suspend fun login(deviceId: String, email: String, name: String) : Long {

        val response = authApi.login(LoginRequestDto(
            deviceId = deviceId, email = email, name = name, appCode = APP_CODE, buildVersion = BUILD_VERSION
        ))

        response.body()?.let { body ->

            if (!response.isSuccessful)
                throw ApiException(code = body.code, httpStatus = body.httpStatus, message = body.message)

            tokenDataStore.setAccessToken(accessToken = body.result.accessToken)
            tokenDataStore.setRefreshToken(refreshToken = body.result.refreshToken)

            return body.result.accountId
        }

        throw InvalidLoginException(email = email)
    }

    /**
     * 로그아웃
     */
    override suspend fun logout() {

        val refreshToken = tokenDataStore.getRefreshToken()

        val response = authApi.logout(LogoutRequestDto(refreshToken = refreshToken))

        response.body()?.let { body ->

            if (!response.isSuccessful)
                throw ApiException(code = body.code, httpStatus = body.httpStatus, message = body.message)

            tokenDataStore.setAccessToken(accessToken = "")
            tokenDataStore.setRefreshToken(refreshToken = "")
        }
    }

    /**
     * 토큰 재발행
     */
    override suspend fun reissue() : String {

        val refreshToken = tokenDataStore.getRefreshToken()

        val response = authApi.reissue(
            com.mongs.wear.data_.auth.dto.request.ReissueRequestDto(
                refreshToken = refreshToken
            )
        )

        response.body()?.let { body ->

            if (!response.isSuccessful)
                throw ApiException(code = body.code, httpStatus = body.httpStatus, message = body.message)

            tokenDataStore.setAccessToken(accessToken = body.result.accessToken)
            tokenDataStore.setRefreshToken(refreshToken = body.result.refreshToken)

            return body.result.accessToken

        } ?: throw InvalidReissueException()
    }

    /**
     * Access Token 조회
     */
    override suspend fun getAccessToken(): String {

        return tokenDataStore.getAccessToken()
    }
}
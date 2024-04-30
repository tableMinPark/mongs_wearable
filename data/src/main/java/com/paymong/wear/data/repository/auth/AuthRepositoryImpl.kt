package com.paymong.wear.data.repository.auth

import android.util.Log
import com.paymong.wear.data.api.auth.AuthApi
import com.paymong.wear.data.api.auth.dto.request.LoginReqDto
import com.paymong.wear.data.api.auth.dto.request.LogoutReqDto
import com.paymong.wear.data.api.auth.dto.request.ReissueReqDto
import com.paymong.wear.data.dataStore.token.TokenDataStore
import com.paymong.wear.domain.error.AuthErrorCode
import com.paymong.wear.domain.exception.AuthException
import com.paymong.wear.domain.repository.auth.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val tokenDataStore: TokenDataStore,
    private val authApi: AuthApi
) : AuthRepository {
    override suspend fun login(deviceId: String, email: String, name: String): Long {
        val response = authApi.login(LoginReqDto(deviceId = deviceId, email = email, name = name))

        if (response.isSuccessful) {
            response.body()?.let { loginResDto ->
                tokenDataStore.modifyAccessToken(accessToken = loginResDto.accessToken)
                tokenDataStore.modifyRefreshToken(refreshToken = loginResDto.refreshToken)

                Log.d("login", "accessToken: [${loginResDto.accessToken}], refreshToken: [${loginResDto.refreshToken}]")

                return loginResDto.accountId
            }
        }
        throw AuthException(AuthErrorCode.LOGIN_FAIL)
    }

    override suspend fun reissue() {
        val refreshToken = tokenDataStore.findRefreshToken()
        val response = authApi.reissue(ReissueReqDto(refreshToken = refreshToken))

        if (response.isSuccessful) {
            response.body()?.let { reissueResDto ->
                tokenDataStore.modifyAccessToken(accessToken = reissueResDto.accessToken)
                tokenDataStore.modifyRefreshToken(refreshToken = reissueResDto.refreshToken)
            }
        } else {
            throw AuthException(AuthErrorCode.REISSUE_FAIL)
        }
    }

    override suspend fun logout(): Long {
        val refreshToken = tokenDataStore.findRefreshToken()
        val response = authApi.logout(LogoutReqDto(refreshToken = refreshToken))

        if (response.isSuccessful) {
            response.body()?.let { logoutResDto ->
                return logoutResDto.accountId
            }
        }

        throw AuthException(AuthErrorCode.LOGOUT_FAIL)
    }
}
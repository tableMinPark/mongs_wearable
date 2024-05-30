package com.paymong.wear.data.repository

import android.util.Log
import com.paymong.wear.data.api.client.AuthApi
import com.paymong.wear.data.dto.auth.req.LoginReqDto
import com.paymong.wear.data.dto.auth.req.LogoutReqDto
import com.paymong.wear.data.dto.auth.req.ReissueReqDto
import com.paymong.wear.domain.error.RepositoryErrorCode
import com.paymong.wear.domain.exception.ApiException
import com.paymong.wear.domain.model.LoginModel
import com.paymong.wear.domain.model.ReissueModel
import com.paymong.wear.domain.repositroy.AuthRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
): AuthRepository {
    override suspend fun login(deviceId: String, email: String, name: String): LoginModel {
        val res = authApi.login(
            LoginReqDto(
                deviceId = deviceId,
                email = email,
                name = name,
            )
        )

        if (res.isSuccessful) {
            res.body()?.let { body ->
                return LoginModel(
                    accountId = body.accountId,
                    accessToken = body.accessToken,
                    refreshToken = body.refreshToken,
                )
            }
        }
        throw ApiException(RepositoryErrorCode.LOGIN_FAIL)
    }
    override suspend fun logout(refreshToken: String): Long {
        val res = authApi.logout(
            LogoutReqDto(
                refreshToken = refreshToken,
            )
        )

        if (res.isSuccessful) {
            res.body()?.let { body ->
                return body.accountId
            }
        }

        throw ApiException(RepositoryErrorCode.LOGOUT_FAIL)
    }
}
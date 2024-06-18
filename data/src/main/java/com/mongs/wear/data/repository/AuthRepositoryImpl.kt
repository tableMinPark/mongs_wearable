package com.mongs.wear.data.repository

import com.mongs.wear.data.api.client.AuthApi
import com.mongs.wear.data.dto.auth.req.LoginReqDto
import com.mongs.wear.data.dto.auth.req.LogoutReqDto
import com.mongs.wear.domain.error.RepositoryErrorCode
import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.model.LoginModel
import com.mongs.wear.domain.repositroy.AuthRepository
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
            val body = res.body()!!

            return LoginModel(
                accountId = body.accountId,
                accessToken = body.accessToken,
                refreshToken = body.refreshToken,
            )
        } else {
            throw RepositoryException(RepositoryErrorCode.LOGIN_FAIL)
        }
    }
    override suspend fun logout(refreshToken: String): Long {
        val res = authApi.logout(
            LogoutReqDto(
                refreshToken = refreshToken,
            )
        )

        if (res.isSuccessful) {
            val body = res.body()!!
            return body.accountId
        } else {
            throw RepositoryException(RepositoryErrorCode.LOGOUT_FAIL)
        }
    }
}
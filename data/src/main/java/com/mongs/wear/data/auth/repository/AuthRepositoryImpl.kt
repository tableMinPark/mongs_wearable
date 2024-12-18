package com.mongs.wear.data.auth.repository

import android.content.Context
import com.mongs.wear.data.R
import com.mongs.wear.data.auth.api.AuthApi
import com.mongs.wear.data.auth.dataStore.TokenDataStore
import com.mongs.wear.data.auth.dto.request.LoginRequestDto
import com.mongs.wear.data.auth.dto.request.LogoutRequestDto
import com.mongs.wear.data.auth.exception.InvalidLoginException
import com.mongs.wear.data.auth.exception.InvalidLogoutException
import com.mongs.wear.domain.auth.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val context: Context,
    private val authApi: AuthApi,
    private val tokenDataStore: TokenDataStore,
) : AuthRepository {

    /**
     * 로그인
     */
    override suspend fun login(deviceId: String, email: String, name: String) : Long {

        val appCode = context.getString(R.string.app_code)

        val buildVersion = context.packageManager.getPackageInfo(context.packageName, 0).versionName

        val response = authApi.login(LoginRequestDto(deviceId = deviceId, email = email, name = name, appCode = appCode, buildVersion = buildVersion))

        if (response.isSuccessful) {
            response.body()?.let { body ->

                tokenDataStore.setAccessToken(accessToken = body.result.accessToken)
                tokenDataStore.setRefreshToken(refreshToken = body.result.refreshToken)

                return body.result.accountId
            }
        }

        throw InvalidLoginException(email = email)
    }

    /**
     * 로그아웃
     */
    override suspend fun logout() {

        val refreshToken = tokenDataStore.getRefreshToken()

        val response = authApi.logout(LogoutRequestDto(refreshToken = refreshToken))

        if (response.isSuccessful) {
            response.body()?.let {

                tokenDataStore.setAccessToken(accessToken = "")
                tokenDataStore.setRefreshToken(refreshToken = "")
            }
        } else {
            throw InvalidLogoutException()
        }
    }
}
package com.mongs.wear.data.auth.repository

import android.content.Context
import com.mongs.wear.data.auth.api.AuthApi
import com.mongs.wear.data.auth.dataStore.TokenDataStore
import com.mongs.wear.data.auth.dto.request.JoinRequestDto
import com.mongs.wear.data.auth.dto.request.LoginRequestDto
import com.mongs.wear.data.auth.dto.request.LogoutRequestDto
import com.mongs.wear.data.auth.exception.JoinException
import com.mongs.wear.data.auth.exception.LoginException
import com.mongs.wear.data.auth.exception.LogoutException
import com.mongs.wear.data.auth.exception.NeedJoinException
import com.mongs.wear.data.auth.exception.NeedUpdateAppException
import com.mongs.wear.data.global.utils.HttpUtil
import com.mongs.wear.domain.auth.repository.AuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val httpUtil: HttpUtil,
    private val authApi: AuthApi,
    private val tokenDataStore: TokenDataStore,
) : AuthRepository {

    /**
     * 회원 가입
     */
    override suspend fun join(email: String, name: String, googleAccountId: String) {

        val response = authApi.join(JoinRequestDto(email = email, name = name, socialAccountId = googleAccountId))

        if (!response.isSuccessful) {
            throw JoinException(result = httpUtil.getErrorResult(response.errorBody()))
        }
    }

    /**
     * 로그인
     */
    override suspend fun login(deviceId: String, email: String, googleAccountId: String) : Long {

        val appPackageName = context.packageName

        val buildVersion = context.packageManager.getPackageInfo(context.packageName, 0).versionName

        val deviceName = android.os.Build.MODEL

        val response = authApi.login(
            LoginRequestDto(
                deviceId = deviceId,
                email = email,
                socialAccountId = googleAccountId,
                appPackageName = appPackageName,
                deviceName = deviceName,
                buildVersion = buildVersion
            )
        )

        if (response.isSuccessful) {
            response.body()?.let { body ->

                tokenDataStore.setAccessToken(accessToken = body.result.accessToken)
                tokenDataStore.setRefreshToken(refreshToken = body.result.refreshToken)

                return body.result.accountId
            }
        }

        val result = httpUtil.getErrorResult(response.errorBody())

        if (response.code() == 406) {
            throw NeedUpdateAppException(result = result)

        } else if (response.code() == 404) {
            throw NeedJoinException(result = result)
        }

        throw LoginException(result = result)
    }

    /**
     * 로그 아웃
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
            throw LogoutException(result = httpUtil.getErrorResult(response.errorBody()))
        }
    }
}
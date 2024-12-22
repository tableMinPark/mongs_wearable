package com.mongs.wear.domain.auth.repository

interface AuthRepository {

    /**
     * 회원가입
     */
    suspend fun join(email: String, name: String, googleAccountId: String)

    /**
     * 로그인
     * @return accountId
     */
    suspend fun login(deviceId: String, email: String, googleAccountId: String) : Long

    /**
     * 로그아웃
     */
    suspend fun logout()
}
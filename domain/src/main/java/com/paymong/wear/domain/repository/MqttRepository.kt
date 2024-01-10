package com.paymong.wear.domain.repository

interface MqttRepository {
    fun connectAfterLogin(email: String)
    fun disConnectAfterLogout()
    fun connectAfterResume()
    fun disConnectAfterPause()
}
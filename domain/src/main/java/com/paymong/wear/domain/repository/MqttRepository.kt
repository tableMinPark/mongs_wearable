package com.paymong.wear.domain.repository

interface MqttRepository {
    fun initDataReset()
    fun connectBeforeInit(email: String)
    fun connectAfterInit()
    fun disConnectNotReset()
    fun disConnectAndReset()
}
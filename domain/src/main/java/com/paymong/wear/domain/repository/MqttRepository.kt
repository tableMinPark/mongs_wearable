package com.paymong.wear.domain.repository

interface MqttRepository {
    fun initMqtt(email: String)
}
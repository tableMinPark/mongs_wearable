package com.mongs.wear.data.global.module

import com.mongs.wear.data.global.client.MqttClientImpl
import com.mongs.wear.domain.global.client.MqttClient
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ClientModule {

    @Binds
    @Singleton
    abstract fun bindMqttClient(client: MqttClientImpl): MqttClient
}
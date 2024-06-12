package com.mongs.wear.data.di

import com.mongs.wear.data.client.MqttClientImpl
import com.mongs.wear.domain.client.MqttClient
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
    abstract fun bindSlotRepository(client: MqttClientImpl): MqttClient
}
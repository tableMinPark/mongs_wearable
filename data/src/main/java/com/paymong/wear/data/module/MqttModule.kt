package com.paymong.wear.data.module

import android.content.Context
import com.paymong.wear.data.mqtt.Mqtt
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MqttModule {
    @Provides
    @Singleton
    fun bindMqtt(@ApplicationContext context: Context) : Mqtt {
        return Mqtt(context)
    }
}
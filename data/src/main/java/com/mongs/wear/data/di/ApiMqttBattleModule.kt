package com.mongs.wear.data.di

import android.content.Context
import com.mongs.wear.data.api.client.MqttBattleApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import info.mqtt.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.MqttClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiMqttBattleModule {
    private const val BASE_URL = "tcp://wearable.mongs.site:1883"
    private val MQTT_CLIENT_ID = MqttClient.generateClientId()

    @Provides
    @Singleton
    fun provideMqttBattle(@ApplicationContext context: Context) : MqttBattleApi {
        return MqttBattleApi(MqttAndroidClient(context, BASE_URL, MQTT_CLIENT_ID))
    }
}
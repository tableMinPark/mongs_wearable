package com.mongs.wear.data_.manager.module

import android.content.Context
import com.google.gson.Gson
import com.mongs.wear.data_.manager.api.ManagementMqttApi
import com.mongs.wear.data_.manager.consumer.ManagementConsumer
import com.mongs.wear.domain.repositroy.ManagementRepository
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
object ManagementMqttApiModule {

    private const val BASE_URL = "tcp://wearable.mongs.site:1883"

    private val MQTT_CLIENT_ID = MqttClient.generateClientId()

    @Provides
    @Singleton
    fun provideManagementMqttApi(gson: Gson, @ApplicationContext context: Context, managementRepository: ManagementRepository): ManagementMqttApi = ManagementMqttApi(
        context = context,
        mqttAndroidClient = MqttAndroidClient(context = context, BASE_URL, MQTT_CLIENT_ID),
        managementConsumer = ManagementConsumer(managementRepository = managementRepository, gson = gson)
    )
}
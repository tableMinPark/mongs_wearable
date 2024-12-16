package com.mongs.wear.data_.activity.module

import android.content.Context
import com.google.gson.Gson
import com.mongs.wear.data_.activity.api.BattleMqttApi
import com.mongs.wear.data_.activity.consumer.BattleConsumer
import com.mongs.wear.domain.repositroy.BattleRepository
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
object BattleMqttApiModule {

    private const val BASE_URL = "tcp://wearable.mongs.site:1883"

    private val MQTT_CLIENT_ID = MqttClient.generateClientId()

    @Provides
    @Singleton
    fun provideBattleMqttApi(@ApplicationContext context: Context, gson: Gson, battleRepository: BattleRepository): BattleMqttApi = BattleMqttApi(
        context = context,
        mqttAndroidClient = MqttAndroidClient(context = context, BASE_URL, MQTT_CLIENT_ID),
        battleConsumer = BattleConsumer(battleRepository = battleRepository, gson = gson)
    )
}
package com.paymong.wear.data.api.notification

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.MqttClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {
    /** Mqtt Server Host **/
    private const val BASE_URL = "tcp://wearable.mongs.site:1883"
    /** Mqtt Client Id **/
    private val MQTT_CLIENT_ID = MqttClient.generateClientId()

    @Provides
    @Singleton
    fun provideMqtt(@ApplicationContext context: Context) : NotificationApi {
        return NotificationApi(context, MqttAndroidClient(context, BASE_URL, MQTT_CLIENT_ID))
    }
}
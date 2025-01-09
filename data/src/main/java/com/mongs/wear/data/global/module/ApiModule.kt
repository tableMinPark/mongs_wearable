package com.mongs.wear.data.global.module

import android.content.Context
import com.google.gson.Gson
import com.mongs.wear.data.R
import com.mongs.wear.data.auth.api.AuthApi
import com.mongs.wear.data.auth.dataStore.TokenDataStore
import com.mongs.wear.data.global.interceptor.AuthorizationInterceptor
import com.mongs.wear.data.global.interceptor.HttpLogInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import info.mqtt.android.service.MqttAndroidClient
import okhttp3.OkHttpClient
import org.eclipse.paho.client.mqttv3.MqttClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideRetrofit(@ApplicationContext context: Context, gson: Gson, authorizationInterceptor: AuthorizationInterceptor, httpLogInterceptor: HttpLogInterceptor) : Retrofit {

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(context.getString(R.string.api_gateway_connect_time_out).toLong(), TimeUnit.SECONDS)
            .readTimeout(context.getString(R.string.api_gateway_read_time_out).toLong(), TimeUnit.SECONDS)
            .writeTimeout(context.getString(R.string.api_gateway_write_time_out).toLong(), TimeUnit.SECONDS)
            .addInterceptor(authorizationInterceptor)
            .addInterceptor(httpLogInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(context.getString(R.string.api_gateway_url))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthorizationInterceptor(authApi: AuthApi, tokenDataStore: TokenDataStore) : AuthorizationInterceptor =
        AuthorizationInterceptor(authApi = authApi, tokenDataStore = tokenDataStore)

    @Provides
    @Singleton
    fun provideHttpLogInterceptor(gson: Gson) : HttpLogInterceptor = HttpLogInterceptor(gson = gson)

    @Provides
    @Singleton
    fun provideMqttAndroidClient(@ApplicationContext context: Context) : MqttAndroidClient = MqttAndroidClient(
        context = context, serverURI = context.getString(R.string.mqtt_url), clientId = MqttClient.generateClientId()
    )
}
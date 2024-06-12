package com.mongs.wear.data.di

import com.mongs.wear.data.api.client.CommonApi
import com.mongs.wear.data.api.interceptor.HttpLogInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiCommonModule {

    private const val COMMON_URL = "http://wearable.mongs.site:8000"
    private const val TIMEOUT = 180L

    @Provides
    @Singleton
    fun provideCommonApi(httpLogInterceptor: HttpLogInterceptor): CommonApi {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(httpLogInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(COMMON_URL)
            .addConverterFactory(GsonConverterFactory.create(ApiModule.gson))
            .client(okHttpClient)
            .build()

        return retrofit.create(CommonApi::class.java)
    }
}

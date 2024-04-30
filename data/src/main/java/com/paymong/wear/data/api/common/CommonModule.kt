package com.paymong.wear.data.api.common

import com.paymong.wear.data.di.ApiModule
import com.paymong.wear.data.api.interceptor.HttpLogInterceptor
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
object CommonModule {
    private const val COMMON_URL = "http://wearable.mongs.site:8002"
//    private const val COMMON_URL = "http://10.0.2.2:8002"

    @Provides
    @Singleton
    fun provideCommonApi(httpLogInterceptor: HttpLogInterceptor): CommonApi {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(ApiModule.TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(ApiModule.TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(ApiModule.TIMEOUT, TimeUnit.SECONDS)
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

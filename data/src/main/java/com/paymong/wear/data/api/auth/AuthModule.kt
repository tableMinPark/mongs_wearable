package com.paymong.wear.data.api.auth

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
object AuthModule {
    private const val AUTH_URL = "http://wearable.mongs.site:8001"
//    private const val AUTH_URL = "http://10.0.2.2:8001"

    @Provides
    @Singleton
    fun provideAuthApi(httpLogInterceptor: HttpLogInterceptor): AuthApi {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(ApiModule.TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(ApiModule.TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(ApiModule.TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(httpLogInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(AUTH_URL)
            .addConverterFactory(GsonConverterFactory.create(ApiModule.gson))
            .client(okHttpClient)
            .build()
        return retrofit.create(AuthApi::class.java)
    }
}

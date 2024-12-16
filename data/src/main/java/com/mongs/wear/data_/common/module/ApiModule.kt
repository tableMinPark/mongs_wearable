package com.mongs.wear.data_.common.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mongs.wear.core.adapter.GsonDateFormatAdapter
import com.mongs.wear.data_.common.interceptor.AuthorizationInterceptor
import com.mongs.wear.data_.common.interceptor.HttpLogInterceptor
import com.mongs.wear.domain.repositroy.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val BASE_URL = "http://wearable.mongs.site:8000"

    private const val TIME_OUT_CONNECT = 300L

    private const val TIME_OUT_READ = 240L

    private const val TIME_OUT_WRITE = 240L

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, authorizationInterceptor: AuthorizationInterceptor, httpLogInterceptor: HttpLogInterceptor) : Retrofit {

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(TIME_OUT_CONNECT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT_READ, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT_WRITE, TimeUnit.SECONDS)
            .addInterceptor(authorizationInterceptor)
            .addInterceptor(httpLogInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthorizationInterceptor(authRepository: AuthRepository) : AuthorizationInterceptor = AuthorizationInterceptor(authRepository = authRepository)

    @Provides
    @Singleton
    fun provideHttpLogInterceptor() : HttpLogInterceptor = HttpLogInterceptor()
}
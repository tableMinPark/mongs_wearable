package com.paymong.wear.data.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.paymong.wear.data.api.interceptor.AuthorizationInterceptor
import com.paymong.wear.data.api.interceptor.HttpLogInterceptor
import com.paymong.wear.data.dataStore.token.TokenDataStore
import com.paymong.wear.data.utils.GsonDateFormatAdapter
import com.paymong.wear.domain.repository.auth.AuthRepository
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
    private const val GATEWAY_URL = "http://wearable.mongs.site:8000"
//    private const val GATEWAY_URL = "http://10.0.2.2:8000"
    const val TIMEOUT = 180L

    val gson: Gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, GsonDateFormatAdapter())
        .create()

    @Provides
    @Singleton
    fun provideRetrofit(
        authorizationInterceptor: AuthorizationInterceptor,
        httpLogInterceptor: HttpLogInterceptor,
    ) : Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(authorizationInterceptor)
            .addInterceptor(httpLogInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(GATEWAY_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }
    @Provides
    @Singleton
    fun provideAuthorizationInterceptor(tokenDataStore: TokenDataStore, authRepository: AuthRepository) : AuthorizationInterceptor {
        return AuthorizationInterceptor(
            tokenDataStore,
            authRepository
        )
    }
    @Provides
    @Singleton
    fun provideHttpLogInterceptor() : HttpLogInterceptor {
        return HttpLogInterceptor()
    }
}

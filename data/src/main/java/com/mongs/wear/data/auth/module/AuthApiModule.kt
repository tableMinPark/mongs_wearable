package com.mongs.wear.data.auth.module

import android.content.Context
import com.google.gson.Gson
import com.mongs.wear.data.R
import com.mongs.wear.data.auth.api.AuthApi
import com.mongs.wear.data.common.interceptor.HttpLogInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthApiModule {

    @Provides
    @Singleton
    fun provideAuthApi(@ApplicationContext context: Context, gson: Gson, httpLogInterceptor: HttpLogInterceptor): AuthApi {

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(context.getString(R.string.api_auth_connect_time_out).toLong(), TimeUnit.SECONDS)
            .readTimeout(context.getString(R.string.api_auth_read_time_out).toLong(), TimeUnit.SECONDS)
            .writeTimeout(context.getString(R.string.api_auth_write_time_out).toLong(), TimeUnit.SECONDS)
            .addInterceptor(httpLogInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(context.getString(R.string.api_auth_url))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

        return retrofit.create(AuthApi::class.java)
    }
}
package com.mongs.wear.data.di

import com.mongs.wear.data.api.client.ManagementApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiManagementModule {
    @Provides
    @Singleton
    fun provideManagementApi(retrofit: Retrofit): ManagementApi {
        return retrofit.create(ManagementApi::class.java)
    }
}

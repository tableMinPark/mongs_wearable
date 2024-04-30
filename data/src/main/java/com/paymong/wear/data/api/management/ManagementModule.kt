package com.paymong.wear.data.api.management

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ManagementModule {
    @Provides
    @Singleton
    fun provideManagementApi(retrofit: Retrofit): ManagementApi {
        return retrofit.create(ManagementApi::class.java)
    }
}

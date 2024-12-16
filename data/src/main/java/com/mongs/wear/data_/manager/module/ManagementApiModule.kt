package com.mongs.wear.data_.manager.module

import com.mongs.wear.data_.manager.api.ManagementApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ManagementApiModule {

    @Provides
    @Singleton
    fun provideManagementApi(retrofit: Retrofit): ManagementApi = retrofit.create(ManagementApi::class.java)
}
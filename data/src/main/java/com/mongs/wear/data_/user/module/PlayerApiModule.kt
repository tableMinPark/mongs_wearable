package com.mongs.wear.data_.user.module

import com.mongs.wear.data_.user.client.PlayerApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PlayerApiModule {

    @Provides
    @Singleton
    fun providePlayerApi(retrofit: Retrofit): PlayerApi = retrofit.create(PlayerApi::class.java)
}
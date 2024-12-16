package com.mongs.wear.data_.activity.module

import com.mongs.wear.data_.activity.api.BattleApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BattleApiModule {

    @Provides
    @Singleton
    fun provideBattleApi(retrofit: Retrofit): BattleApi =
        retrofit.create(BattleApi::class.java)
}
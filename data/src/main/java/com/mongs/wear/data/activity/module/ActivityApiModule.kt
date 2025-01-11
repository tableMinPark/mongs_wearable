package com.mongs.wear.data.activity.module

import com.mongs.wear.data.activity.api.BattleApi
import com.mongs.wear.data.activity.api.TrainingApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ActivityApiModule {

    @Provides
    @Singleton
    fun provideBattleApi(retrofit: Retrofit): BattleApi = retrofit.create(BattleApi::class.java)

    @Provides
    @Singleton
    fun provideTrainingApi(retrofit: Retrofit): TrainingApi = retrofit.create(TrainingApi::class.java)
}
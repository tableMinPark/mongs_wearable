package com.mongs.wear.data_.user.module

import com.mongs.wear.data_.user.client.FeedbackApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FeedbackApiModule {

    @Provides
    @Singleton
    fun provideFeedbackApi(retrofit: Retrofit): FeedbackApi = retrofit.create(FeedbackApi::class.java)
}
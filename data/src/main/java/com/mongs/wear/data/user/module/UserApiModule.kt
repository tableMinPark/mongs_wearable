package com.mongs.wear.data.user.module

import com.mongs.wear.data.user.api.CollectionApi
import com.mongs.wear.data.user.api.FeedbackApi
import com.mongs.wear.data.user.api.PlayerApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserApiModule {

    @Provides
    @Singleton
    fun provideCollectionApi(retrofit: Retrofit): CollectionApi = retrofit.create(CollectionApi::class.java)

    @Provides
    @Singleton
    fun provideFeedbackApi(retrofit: Retrofit): FeedbackApi = retrofit.create(FeedbackApi::class.java)

    @Provides
    @Singleton
    fun providePlayerApi(retrofit: Retrofit): PlayerApi = retrofit.create(PlayerApi::class.java)
}
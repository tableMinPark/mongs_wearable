package com.paymong.wear.data.api.feedback

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FeedbackModule {
    @Provides
    @Singleton
    fun provideFeedbackApi(retrofit: Retrofit): FeedbackApi {
        return retrofit.create(FeedbackApi::class.java)
    }
}

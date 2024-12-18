package com.mongs.wear.data.common.module

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mongs.wear.core.adapter.GsonDateFormatAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.time.LocalDateTime
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilModule {

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context = context

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, GsonDateFormatAdapter())
        .create()
}
package com.mongs.wear.data_.common.module

import android.content.Context
import com.mongs.wear.data_.common.datastore.AppDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppDataStoreModule {

    @Provides
    @Singleton
    fun bindAppDataStore(@ApplicationContext context: Context): AppDataStore = AppDataStore(context)
}
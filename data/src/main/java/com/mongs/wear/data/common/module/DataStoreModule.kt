package com.mongs.wear.data.common.module

import android.content.Context
import com.mongs.wear.data.common.datastore.AppDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun bindAppDataStore(@ApplicationContext context: Context): AppDataStore = AppDataStore(context)
}
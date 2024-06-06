package com.mongs.wear.data.di

import android.content.Context
import androidx.room.Room
import com.mongs.wear.data.room.client.RoomDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    @Singleton
    fun provideMongDatabase(@ApplicationContext context: Context) : RoomDB {
        return Room
            .databaseBuilder(context.applicationContext, RoomDB::class.java, "mongs-database")
            .fallbackToDestructiveMigration().build()
    }
}
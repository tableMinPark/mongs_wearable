package com.mongs.wear.data.global.module

import android.content.Context
import androidx.room.Room
import com.mongs.wear.data.global.room.RoomDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val ROOM_NAME = "MONGS-DATABASE"

    @Provides
    @Singleton
    fun provideMongDatabase(@ApplicationContext context: Context): RoomDB =
        Room.databaseBuilder(context.applicationContext, RoomDB::class.java, ROOM_NAME)
            .fallbackToDestructiveMigration()
            .build()
}
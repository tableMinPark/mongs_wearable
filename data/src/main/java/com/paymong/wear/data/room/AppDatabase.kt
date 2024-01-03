package com.paymong.wear.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.paymong.wear.data.entity.Feed
import com.paymong.wear.data.room.dao.MongDao
import com.paymong.wear.data.entity.Mong
import com.paymong.wear.data.room.dao.FeedDao
import com.paymong.wear.data.room.util.Converters

@Database(entities = [Mong::class, Feed::class], version = 30)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mongDao(): MongDao
    abstract fun feedDao(): FeedDao
}
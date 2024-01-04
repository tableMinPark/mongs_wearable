package com.paymong.wear.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.paymong.wear.data.entity.Character
import com.paymong.wear.data.entity.Feed
import com.paymong.wear.data.room.dao.MongDao
import com.paymong.wear.data.entity.Mong
import com.paymong.wear.data.room.dao.CharacterDao
import com.paymong.wear.data.room.dao.FeedDao
import com.paymong.wear.data.room.util.Converters

@Database(entities = [Mong::class, Feed::class, Character::class], version = 31)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mongDao(): MongDao
    abstract fun feedDao(): FeedDao
    abstract fun characterDao(): CharacterDao
}
package com.paymong.wear.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.paymong.wear.data.room.entity.Mong
import com.paymong.wear.data.room.entity.Feed
import com.paymong.wear.data.room.dao.SlotDao
import com.paymong.wear.data.room.entity.Slot
import com.paymong.wear.data.room.dao.MongDao
import com.paymong.wear.data.room.dao.FeedDao
import com.paymong.wear.data.room.util.Converters

@Database(entities = [Slot::class, Feed::class, Mong::class], version = 34)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun slotBySlotIdDao(): SlotDao
    abstract fun feedDao(): FeedDao
    abstract fun mongDao(): MongDao
}
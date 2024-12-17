package com.mongs.wear.data.common.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mongs.wear.data.activity.dao.MatchPlayerDao
import com.mongs.wear.data.activity.dao.MatchRoomDao
import com.mongs.wear.data.activity.entity.MatchPlayerEntity
import com.mongs.wear.data.activity.entity.MatchRoomEntity
import com.mongs.wear.data.manager.dao.MongDao
import com.mongs.wear.data.manager.entity.MongEntity

@Database(entities = [MatchPlayerEntity::class, MatchRoomEntity::class, MongEntity::class], version = 100)
@TypeConverters(RoomConverters::class)
abstract class RoomDB : RoomDatabase() {

    abstract fun mongDao(): MongDao

    abstract fun matchPlayerDao(): MatchPlayerDao

    abstract fun matchRoomDao(): MatchRoomDao

//    abstract fun feedbackLogDao(): FeedbackLogDao
}
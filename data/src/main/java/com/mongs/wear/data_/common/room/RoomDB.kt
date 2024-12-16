package com.mongs.wear.data_.common.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mongs.wear.data_.activity.entity.MatchPlayerEntity
import com.mongs.wear.data_.activity.entity.MatchRoomEntity
import com.mongs.wear.data_.manager.entity.MongEntity

@Database(entities = [MatchPlayerEntity::class, MatchRoomEntity::class, MongEntity::class], version = 100)
@TypeConverters(RoomConverters::class)
abstract class RoomDB : RoomDatabase() {

//    abstract fun feedbackLogDao(): FeedbackLogDao
}
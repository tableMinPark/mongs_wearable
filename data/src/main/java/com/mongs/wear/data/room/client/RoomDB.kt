package com.mongs.wear.data.room.client

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mongs.wear.data.room.entity.MongCode
import com.mongs.wear.data.room.entity.FoodCode
import com.mongs.wear.data.room.entity.Slot
import com.mongs.wear.data.room.entity.FeedbackLog
import com.mongs.wear.data.room.entity.MapCode
import com.mongs.wear.data.room.dao.FeedbackLogDao
import com.mongs.wear.data.room.dao.FoodCodeDao
import com.mongs.wear.data.room.dao.MapCodeDao
import com.mongs.wear.data.room.dao.MatchDao
import com.mongs.wear.data.room.dao.MatchPlayerDao
import com.mongs.wear.data.room.dao.MongCodeDao
import com.mongs.wear.data.room.dao.SlotDao
import com.mongs.wear.data.room.entity.Match
import com.mongs.wear.data.room.entity.MatchPlayer

@Database(entities = [
    FeedbackLog::class,
    FoodCode::class,
    MapCode::class,
    MongCode::class,
    Slot::class,
    Match::class,
    MatchPlayer::class,
], version = 81)
@TypeConverters(Converters::class)
abstract class RoomDB : RoomDatabase() {
    abstract fun feedbackLogDao(): FeedbackLogDao
    abstract fun foodCodeDao(): FoodCodeDao
    abstract fun mapCodeDao(): MapCodeDao
    abstract fun mongCodeDao(): MongCodeDao
    abstract fun slotDao(): SlotDao
    abstract fun matchDao(): MatchDao
    abstract fun matchPlayerDao(): MatchPlayerDao
}
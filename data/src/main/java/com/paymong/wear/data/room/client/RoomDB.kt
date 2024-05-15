package com.paymong.wear.data.room.client

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.paymong.wear.data.room.entity.MongCode
import com.paymong.wear.data.room.entity.FoodCode
import com.paymong.wear.data.room.entity.Slot
import com.paymong.wear.data.room.entity.FeedbackLog
import com.paymong.wear.data.room.entity.MapCode
import com.paymong.wear.data.room.dao.FeedbackLogDao
import com.paymong.wear.data.room.dao.FoodCodeDao
import com.paymong.wear.data.room.dao.MapCodeDao
import com.paymong.wear.data.room.dao.MongCodeDao
import com.paymong.wear.data.room.dao.SlotDao

@Database(entities = [
    FeedbackLog::class,
    FoodCode::class,
    MapCode::class,
    MongCode::class,
    Slot::class], version = 75)
@TypeConverters(Converters::class)
abstract class RoomDB : RoomDatabase() {
    abstract fun feedbackLogDao(): FeedbackLogDao
    abstract fun foodCodeDao(): FoodCodeDao
    abstract fun mapCodeDao(): MapCodeDao
    abstract fun mongCodeDao(): MongCodeDao
    abstract fun slotDao(): SlotDao
}
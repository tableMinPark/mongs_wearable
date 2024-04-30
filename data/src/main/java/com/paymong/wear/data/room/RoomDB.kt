package com.paymong.wear.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.paymong.wear.data.room.mongCode.MongCode
import com.paymong.wear.data.room.foodCode.FoodCode
import com.paymong.wear.data.room.slot.Slot
import com.paymong.wear.data.room.feedback.FeedbackLog
import com.paymong.wear.data.room.mapCode.MapCode
import com.paymong.wear.data.room.feedback.FeedbackLogDao
import com.paymong.wear.data.room.feedbackCode.FeedbackCode
import com.paymong.wear.data.room.feedbackCode.FeedbackCodeDao
import com.paymong.wear.data.room.foodCode.FoodCodeDao
import com.paymong.wear.data.room.mapCode.MapCodeDao
import com.paymong.wear.data.room.mongCode.MongCodeDao
import com.paymong.wear.data.room.slot.SlotDao
import com.paymong.wear.data.utils.Converters

@Database(entities = [
    FeedbackLog::class,
    FoodCode::class,
    MapCode::class,
    FeedbackCode::class,
    MongCode::class,
    Slot::class], version = 75)
@TypeConverters(Converters::class)
abstract class RoomDB : RoomDatabase() {
    abstract fun feedbackLogDao(): FeedbackLogDao
    abstract fun foodCodeDao(): FoodCodeDao
    abstract fun mapCodeDao(): MapCodeDao
    abstract fun mongCodeDao(): MongCodeDao
    abstract fun feedbackCodeDao(): FeedbackCodeDao
    abstract fun slotDao(): SlotDao
}
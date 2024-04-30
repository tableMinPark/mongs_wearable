package com.paymong.wear.data.room.feedbackCode

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FeedbackCodeDao {
    @Query("SELECT * FROM feedback_code")
    fun findAll(): List<FeedbackCode>
    @Query("SELECT DISTINCT groupCode FROM feedback_code")
    fun findGroupCode(): List<String>
    @Insert
    fun register(feedbackCode: FeedbackCode)
    @Query("DELETE FROM feedback_code")
    fun deleteAll()
    @Query("SELECT * FROM feedback_code WHERE groupCode = :groupCode")
    fun findByGroupCode(groupCode: String): List<FeedbackCode>
}
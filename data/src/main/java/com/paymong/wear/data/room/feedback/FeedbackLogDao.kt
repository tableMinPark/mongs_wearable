package com.paymong.wear.data.room.feedback

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FeedbackLogDao {
    @Insert
    fun register(feedbackLog: FeedbackLog)
    @Query("SELECT * FROM feedback_log WHERE groupCode = :groupCode")
    fun findByGroupCode(groupCode: String): List<FeedbackLog>
}
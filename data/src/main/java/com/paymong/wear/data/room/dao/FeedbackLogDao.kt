package com.paymong.wear.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.paymong.wear.data.room.entity.FeedbackLog

@Dao
interface FeedbackLogDao {
    @Insert
    fun register(feedbackLog: FeedbackLog)
    @Query("SELECT * FROM feedback_log WHERE groupCode = :groupCode")
    fun findByGroupCode(groupCode: String): List<FeedbackLog>
}
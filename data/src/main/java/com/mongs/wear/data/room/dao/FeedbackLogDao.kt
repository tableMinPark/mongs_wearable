package com.mongs.wear.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mongs.wear.data.room.entity.FeedbackLog
import java.time.LocalDateTime

@Dao
interface FeedbackLogDao {
    @Insert
    fun insert(feedbackLog: FeedbackLog)
    @Query("SELECT * FROM feedback_log WHERE groupCode = :groupCode")
    fun selectByGroupCode(groupCode: String): List<FeedbackLog>
    @Query("DELETE FROM feedback_log WHERE createdAt < :createdAt")
    fun deleteByCreatedAt(createdAt: LocalDateTime)
}
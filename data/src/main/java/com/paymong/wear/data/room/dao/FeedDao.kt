package com.paymong.wear.data.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.paymong.wear.data.room.entity.Feed
import com.paymong.wear.domain.model.FeedModel

@Dao
interface FeedDao {
    @Insert
    fun registerFeed(feed: Feed)
    @Query("DELETE FROM feed")
    fun deleteAllFeed()
    @Query("SELECT * FROM feed WHERE feedCode = :feedCode")
    fun findAllFeed(feedCode: String): LiveData<List<FeedModel>>
}
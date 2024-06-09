package com.mongs.wear.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mongs.wear.data.room.entity.FoodCode

@Dao
interface FoodCodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(foodCode: FoodCode)
    @Query("DELETE FROM food_code")
    fun deleteAll()
    @Query("SELECT * FROM food_code WHERE groupCode = :groupCode")
    fun selectByGroupCode(groupCode: String): List<FoodCode>
}
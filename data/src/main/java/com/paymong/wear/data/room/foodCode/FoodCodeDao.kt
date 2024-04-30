package com.paymong.wear.data.room.foodCode

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FoodCodeDao {
    @Query("SELECT * FROM food_code")
    fun findAll(): List<FoodCode>
    @Query("SELECT COUNT(*) FROM food_code")
    fun countAll(): Int
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun register(foodCode: FoodCode)
    @Query("DELETE FROM food_code")
    fun deleteAll()
    @Query("SELECT * FROM food_code WHERE groupCode = :groupCode")
    fun findByGroupCode(groupCode: String): List<FoodCode>
}
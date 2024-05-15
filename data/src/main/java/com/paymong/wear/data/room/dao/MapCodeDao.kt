package com.paymong.wear.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.paymong.wear.data.room.entity.MapCode

@Dao
interface MapCodeDao {
    @Query("SELECT * FROM map_code")
    fun findAll(): List<MapCode>
    @Query("SELECT COUNT(*) FROM map_code")
    fun countAll(): Int
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun register(mapCode: MapCode)
    @Query("DELETE FROM map_code")
    fun deleteAll()
    @Query("SELECT * FROM map_code WHERE code = :code")
    fun findByCode(code: String): MapCode
}
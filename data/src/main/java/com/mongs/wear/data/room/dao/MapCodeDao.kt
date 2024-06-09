package com.mongs.wear.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mongs.wear.data.room.entity.MapCode

@Dao
interface MapCodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(mapCode: MapCode)
    @Query("DELETE FROM map_code")
    fun deleteAll()
    @Query("SELECT * FROM map_code WHERE code = :code")
    fun selectByCode(code: String): MapCode
}
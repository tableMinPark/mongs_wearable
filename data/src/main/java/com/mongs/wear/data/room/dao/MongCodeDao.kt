package com.mongs.wear.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mongs.wear.data.room.entity.MongCode

@Dao
interface MongCodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(mongCode: MongCode)
    @Query("DELETE FROM mong_code")
    fun deleteAll()
    @Query("SELECT * FROM mong_code WHERE code = :code")
    fun selectByCode(code: String): MongCode
}
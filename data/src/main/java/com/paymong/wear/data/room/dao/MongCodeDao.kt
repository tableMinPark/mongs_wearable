package com.paymong.wear.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.paymong.wear.data.room.entity.MongCode

@Dao
interface MongCodeDao {
    @Query("SELECT * FROM mong_code")
    fun findAll(): List<MongCode>
    @Query("SELECT COUNT(*) FROM mong_code")
    fun countAll(): Int
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun register(mongCode: MongCode)
    @Query("DELETE FROM mong_code")
    fun deleteAll()
    @Query("SELECT * FROM mong_code WHERE code = :code")
    fun findByCode(code: String): MongCode
}
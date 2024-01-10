package com.paymong.wear.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.paymong.wear.data.room.entity.Mong
import com.paymong.wear.domain.model.MongModel

@Dao
interface MongDao {
    @Insert
    fun register(mong: Mong)
    @Query("DELETE FROM mong")
    fun deleteAll()
    @Query("SELECT * FROM mong WHERE code = :code")
    fun findByCode(code: String): MongModel
}
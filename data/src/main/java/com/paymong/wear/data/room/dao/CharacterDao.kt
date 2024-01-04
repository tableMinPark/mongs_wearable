package com.paymong.wear.data.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.paymong.wear.data.entity.Character
import com.paymong.wear.data.entity.Feed
import com.paymong.wear.domain.model.CharacterModel
import com.paymong.wear.domain.model.FeedModel

@Dao
interface CharacterDao {
    @Insert
    fun registerCharacter(character: Character)
    @Query("DELETE FROM character")
    fun deleteAllCharacter()
    @Query("SELECT * FROM character WHERE code = :code")
    fun findCharacter(code: String): CharacterModel
}
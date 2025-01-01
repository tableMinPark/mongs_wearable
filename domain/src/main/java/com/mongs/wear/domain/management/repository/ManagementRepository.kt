package com.mongs.wear.domain.management.repository

import com.mongs.wear.domain.management.model.FeedItemModel
import com.mongs.wear.domain.management.model.MongModel

interface ManagementRepository {

    suspend fun getMongs(): List<MongModel>

    suspend fun createMong(name: String, sleepStart: String, sleepEnd: String)

    suspend fun deleteMong(mongId: Long)

    suspend fun getFeedItems(mongId: Long, foodTypeGroupCode: String): List<FeedItemModel>

    suspend fun feedMong(mongId: Long, foodTypeCode: String)

    suspend fun graduateMong(mongId: Long)

    suspend fun graduateCheckMong(mongId: Long)

    suspend fun evolutionMong(mongId: Long)

    suspend fun sleepingMong(mongId: Long)

    suspend fun strokeMong(mongId: Long)

//    suspend fun trainingMong(mongId: Long, trainingCode: String, score: Int)

    suspend fun poopCleanMong(mongId: Long)

    suspend fun setIsHappy(mongId: Long)

    suspend fun setIsEating(mongId: Long)

    suspend fun setIsPoopCleaning(mongId: Long)
}
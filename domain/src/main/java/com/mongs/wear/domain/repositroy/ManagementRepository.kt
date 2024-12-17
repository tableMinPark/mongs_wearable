package com.mongs.wear.domain.repositroy

import com.mongs.wear.domain.model.FeedItemModel

interface ManagementRepository {

    suspend fun createMong(name: String, sleepStart: String, sleepEnd: String)

    suspend fun deleteMong(mongId: Long)

    suspend fun getFeedLog(mongId: Long): List<FeedItemModel>

    suspend fun feedMong(mongId: Long, code: String)

    suspend fun graduateMong(mongId: Long)

    suspend fun graduateReadyMong(mongId: Long)

    suspend fun evolutionMong(mongId: Long)

    suspend fun sleepingMong(mongId: Long)

    suspend fun strokeMong(mongId: Long)

    suspend fun trainingMong(mongId: Long, trainingCode: String, score: Int)

    suspend fun poopCleanMong(mongId: Long)

    suspend fun setIsHappy(mongId: Long, isHappy: Boolean)

    suspend fun setIsEating(mongId: Long, isEating: Boolean)

    suspend fun setIsPoopCleaning(mongId: Long, isPoopCleaning: Boolean)


}
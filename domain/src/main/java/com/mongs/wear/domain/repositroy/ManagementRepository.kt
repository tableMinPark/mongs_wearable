package com.mongs.wear.domain.repositroy

import com.mongs.wear.domain.model.FeedLogModel

interface ManagementRepository {
    suspend fun add(name: String, sleepStart: String, sleepEnd: String)
    suspend fun delete(mongId: Long)
    suspend fun getFeedLog(mongId: Long): List<FeedLogModel>
    suspend fun feed(mongId: Long, code: String)
    suspend fun graduate(mongId: Long)
    suspend fun evolution(mongId: Long)
    suspend fun sleeping(mongId: Long)
    suspend fun stroke(mongId: Long)
    suspend fun poopClean(mongId: Long)
}
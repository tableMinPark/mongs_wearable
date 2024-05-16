package com.paymong.wear.domain.repositroy

import com.paymong.wear.domain.model.FeedLogModel

interface ManagementRepository {
    suspend fun add(name: String, sleepStart: String, sleepEnd: String)
    suspend fun delete(mongId: Long)
    suspend fun getFeedLog(mongId: Long): List<FeedLogModel>
    suspend fun feed(mongId: Long, code: String)
    suspend fun graduate(mongId: Long)
    suspend fun evolution(mongId: Long)
    suspend fun sleep(mongId: Long)
    suspend fun awake(mongId: Long)
    suspend fun stroke(mongId: Long)
    suspend fun poopClean(mongId: Long)
}
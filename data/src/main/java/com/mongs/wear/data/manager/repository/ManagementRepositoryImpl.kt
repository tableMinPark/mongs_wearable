package com.mongs.wear.data.manager.repository

import android.util.Log
import com.mongs.wear.domain.model.FeedItemModel
import com.mongs.wear.domain.repositroy.ManagementRepository
import javax.inject.Inject

class ManagementRepositoryImpl @Inject constructor(
): ManagementRepository {

    override suspend fun createMong(name: String, sleepStart: String, sleepEnd: String) {
        Log.d("ManagementRepositoryImpl", "createMong")
    }

    override suspend fun deleteMong(mongId: Long) {
        Log.d("ManagementRepositoryImpl", "deleteMong")
    }

    override suspend fun getFeedLog(mongId: Long): List<FeedItemModel> {
        Log.d("ManagementRepositoryImpl", "getFeedLog")
        return ArrayList();
    }

    override suspend fun feedMong(mongId: Long, code: String) {
        Log.d("ManagementRepositoryImpl", "feedMong")
    }

    override suspend fun graduateMong(mongId: Long) {
        Log.d("ManagementRepositoryImpl", "graduateMong")
    }

    override suspend fun graduateReadyMong(mongId: Long) {
        Log.d("ManagementRepositoryImpl", "graduateReadyMong")
    }

    override suspend fun evolutionMong(mongId: Long) {
        Log.d("ManagementRepositoryImpl", "evolutionMong")
    }

    override suspend fun sleepingMong(mongId: Long) {
        Log.d("ManagementRepositoryImpl", "sleepingMong")
    }

    override suspend fun strokeMong(mongId: Long) {
        Log.d("ManagementRepositoryImpl", "strokeMong")
    }

    override suspend fun trainingMong(mongId: Long, trainingCode: String, score: Int) {
        Log.d("ManagementRepositoryImpl", "trainingMong")
    }

    override suspend fun poopCleanMong(mongId: Long) {
        Log.d("ManagementRepositoryImpl", "poopCleanMong")
    }

    override suspend fun setIsHappy(mongId: Long, isHappy: Boolean) {
        Log.d("ManagementRepositoryImpl", "setIsHappy")
    }

    override suspend fun setIsEating(mongId: Long, isEating: Boolean) {
        Log.d("ManagementRepositoryImpl", "setIsEating")
    }

    override suspend fun setIsPoopCleaning(mongId: Long, isPoopCleaning: Boolean) {
        Log.d("ManagementRepositoryImpl", "setIsPoopCleaning")
    }
}
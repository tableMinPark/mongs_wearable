package com.mongs.wear.domain.training.repository

interface TrainingRepository {

    suspend fun trainingRunner(mongId: Long, score: Int)
}
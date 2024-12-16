package com.mongs.wear.data_.user.repository

import com.mongs.wear.domain.repositroy.FeedbackRepository
import javax.inject.Inject

class FeedbackRepositoryImpl @Inject constructor(

) : FeedbackRepository {

    override suspend fun createFeedback(title: String, content: String) {
        TODO("Not yet implemented")
    }
}
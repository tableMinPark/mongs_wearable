package com.mongs.wear.domain.usecase.collection

import com.mongs.wear.domain.code.FeedbackCode
import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.repositroy.CollectionRepository
import com.mongs.wear.domain.repositroy.FeedbackRepository
import com.mongs.wear.domain.vo.MongCollectionVo
import javax.inject.Inject

class GetMongCollectionsUseCase @Inject constructor(
    private val collectionRepository: CollectionRepository,
    private val feedbackRepository: FeedbackRepository,
) {
    suspend operator fun invoke(): List<MongCollectionVo> {
        try {
            val mapCollectionModels = collectionRepository.getMongCollections()
            return mapCollectionModels.map {
                MongCollectionVo(
                    code = it.code,
                    name = it.name,
                    disable = it.disable
                )
            }
        } catch (e: RepositoryException) {
            feedbackRepository.addFeedbackLog(
                groupCode = FeedbackCode.COLLECTION.groupCode,
                location = "GetMongCollectionsUseCase",
                message = e.stackTrace.contentDeepToString(),
            )

            throw UseCaseException(e.errorCode, e)
        }
    }
}
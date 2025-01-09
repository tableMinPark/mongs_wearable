package com.mongs.wear.domain.collection.usecase

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.collection.exception.GetMongCollectionException
import com.mongs.wear.domain.collection.repository.CollectionRepository
import com.mongs.wear.domain.collection.vo.MongCollectionVo
import com.mongs.wear.domain.global.usecase.BaseNoParamUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetMongCollectionsUseCase @Inject constructor(
    private val collectionRepository: CollectionRepository,
) : BaseNoParamUseCase<List<MongCollectionVo>>() {

    override suspend fun execute(): List<MongCollectionVo> {

        return withContext(Dispatchers.IO) {
            collectionRepository.getMongCollections().map {
                MongCollectionVo(
                    code = it.code,
                    name = it.name,
                    isIncluded = it.isIncluded
                )
            }
        }
    }

    override fun handleException(exception: ErrorException) {
        super.handleException(exception)

        when(exception.code) {
            else -> throw GetMongCollectionException()
        }
    }
}
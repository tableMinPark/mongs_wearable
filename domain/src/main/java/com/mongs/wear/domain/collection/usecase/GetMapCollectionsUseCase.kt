package com.mongs.wear.domain.collection.usecase

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.collection.exception.GetMapCollectionsException
import com.mongs.wear.domain.collection.repository.CollectionRepository
import com.mongs.wear.domain.collection.vo.MapCollectionVo
import com.mongs.wear.domain.global.usecase.BaseNoParamUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetMapCollectionsUseCase @Inject constructor(
    private val collectionRepository: CollectionRepository,
) : BaseNoParamUseCase<List<MapCollectionVo>>() {

    override suspend fun execute(): List<MapCollectionVo> {

        return withContext(Dispatchers.IO) {
            collectionRepository.getMapCollections().map {
                MapCollectionVo(
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
            else -> throw GetMapCollectionsException()
        }
    }
}
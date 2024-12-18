package com.mongs.wear.domain.collection.usecase

import com.mongs.wear.domain.collection.repository.CollectionRepository
import com.mongs.wear.domain.collection.vo.MongCollectionVo
import javax.inject.Inject

class GetMongCollectionsUseCase @Inject constructor(
    private val collectionRepository: CollectionRepository,
) {
    suspend operator fun invoke(): List<MongCollectionVo> =
        collectionRepository.getMongCollections().map {
            MongCollectionVo(
                code = it.code,
                name = it.name,
                isIncluded = it.isIncluded
            )
        }
}
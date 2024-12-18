package com.mongs.wear.domain.collection.usecase

import com.mongs.wear.domain.collection.repository.CollectionRepository
import com.mongs.wear.domain.collection.vo.MapCollectionVo
import javax.inject.Inject

class GetMapCollectionsUseCase @Inject constructor(
    private val collectionRepository: CollectionRepository,
) {
    suspend operator fun invoke(): List<MapCollectionVo> =
        collectionRepository.getMapCollections().map {
            MapCollectionVo(
                code = it.code,
                name = it.name,
                disable = it.isIncluded
            )
        }
}
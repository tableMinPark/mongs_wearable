package com.mongs.wear.domain.usecase.collection

import com.mongs.wear.domain.repositroy.CollectionRepository
import com.mongs.wear.domain.vo.MapCollectionVo
import javax.inject.Inject

class GetMapCollectionsUseCase @Inject constructor(
    private val collectionRepository: CollectionRepository,
) {
    suspend operator fun invoke(): List<MapCollectionVo> = collectionRepository.getMapCollections().map {
        MapCollectionVo(
            code = it.code,
            name = it.name,
            disable = it.isIncluded
        )
    }
}
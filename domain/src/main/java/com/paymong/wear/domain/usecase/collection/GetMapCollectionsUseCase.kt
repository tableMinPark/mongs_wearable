package com.paymong.wear.domain.usecase.collection

import com.paymong.wear.domain.repositroy.CollectionRepository
import com.paymong.wear.domain.vo.MapCollectionVo
import javax.inject.Inject

class GetMapCollectionsUseCase @Inject constructor(
    private val collectionRepository: CollectionRepository
) {
    suspend operator fun invoke(): List<MapCollectionVo> {
        val mapCollectionModels = collectionRepository.getMapCollections()
        return mapCollectionModels.map { MapCollectionVo(code = it.code, name = it.name, disable = it.disable) }
    }
}
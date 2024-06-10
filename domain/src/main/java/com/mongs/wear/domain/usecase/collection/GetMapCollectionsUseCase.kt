package com.mongs.wear.domain.usecase.collection

import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.repositroy.CollectionRepository
import com.mongs.wear.domain.vo.MapCollectionVo
import javax.inject.Inject

class GetMapCollectionsUseCase @Inject constructor(
    private val collectionRepository: CollectionRepository
) {
    suspend operator fun invoke(): List<MapCollectionVo> {
        try {
            val mapCollectionModels = collectionRepository.getMapCollections()
            return mapCollectionModels.map {
                MapCollectionVo(
                    code = it.code,
                    name = it.name,
                    disable = it.disable
                )
            }
        } catch (e: RepositoryException) {
            throw UseCaseException(e.errorCode)
        }
    }
}
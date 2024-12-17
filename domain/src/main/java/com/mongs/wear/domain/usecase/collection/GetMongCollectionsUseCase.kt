package com.mongs.wear.domain.usecase.collection

import com.mongs.wear.domain.repositroy.CollectionRepository
import com.mongs.wear.domain.vo.MongCollectionVo
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
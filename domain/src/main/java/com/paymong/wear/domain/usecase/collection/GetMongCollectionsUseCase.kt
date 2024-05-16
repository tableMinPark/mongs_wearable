package com.paymong.wear.domain.usecase.collection

import com.paymong.wear.domain.repositroy.CollectionRepository
import com.paymong.wear.domain.vo.MongCollectionVo
import javax.inject.Inject

class GetMongCollectionsUseCase @Inject constructor(
    private val collectionRepository: CollectionRepository
) {
    suspend operator fun invoke(): List<MongCollectionVo> {
        val mapCollectionModels = collectionRepository.getMongCollections()
        return mapCollectionModels.map { MongCollectionVo(code = it.code, name = it.name, disable = it.disable) }
    }
}
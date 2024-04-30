package com.paymong.wear.domain.repository.collection

import com.paymong.wear.domain.repository.collection.vo.CollectionVo

interface CollectionRepository {
    suspend fun getAllCollectionMap() : List<CollectionVo>
    suspend fun getAllCollectionMong() : List<CollectionVo>
}
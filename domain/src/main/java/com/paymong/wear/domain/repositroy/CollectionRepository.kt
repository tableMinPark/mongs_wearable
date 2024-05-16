package com.paymong.wear.domain.repositroy

import com.paymong.wear.domain.model.CollectionModel

interface CollectionRepository {
    suspend fun getMapCollections(): List<CollectionModel>
    suspend fun getMongCollections(): List<CollectionModel>
}
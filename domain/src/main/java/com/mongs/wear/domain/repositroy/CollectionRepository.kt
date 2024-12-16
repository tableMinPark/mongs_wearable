package com.mongs.wear.domain.repositroy

import com.mongs.wear.domain.model.CollectionModel

interface CollectionRepository {

    suspend fun getMapCollections(): List<CollectionModel>

    suspend fun getMongCollections(): List<CollectionModel>
}
package com.mongs.wear.domain.repositroy

import com.mongs.wear.domain.model.CollectionModel

interface CollectionRepository {

    /**
     * 컬렉션 맵 목록 조회
     */
    suspend fun getMapCollections(): List<CollectionModel>

    /**
     * 컬렉션 몽 목록 조회
     */
    suspend fun getMongCollections(): List<CollectionModel>
}
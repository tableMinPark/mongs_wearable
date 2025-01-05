package com.mongs.wear.data.user.repository

import com.mongs.wear.data.user.api.CollectionApi
import com.mongs.wear.data.user.exception.GetMapCollectionsException
import com.mongs.wear.data.user.exception.GetMongCollectionsException
import com.mongs.wear.domain.collection.model.CollectionModel
import com.mongs.wear.domain.collection.repository.CollectionRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CollectionRepositoryImpl @Inject constructor(
    private val collectionApi: CollectionApi,
): CollectionRepository {

    /**
     * 컬렉션 맵 목록 조회
     */
    override suspend fun getMapCollections(): List<CollectionModel> {

        val response = collectionApi.getCollectionMaps()

        if (response.isSuccessful) {
            response.body()?.let { body ->
                return body.result.map {
                    CollectionModel(
                        code = it.mapTypeCode,
                        name = it.mapTypeName,
                        isIncluded = it.isIncluded,
                    )
                }
            }
        }

        throw GetMapCollectionsException()
    }

    /**
     * 컬렉션 몽 목록 조회
     */
    override suspend fun getMongCollections(): List<CollectionModel> {

        val response = collectionApi.getCollectionMongs()

        if (response.isSuccessful) {
            response.body()?.let { body ->
                return body.result.map {
                    CollectionModel(
                        code = it.mongTypeCode,
                        name = it.mongTypeName,
                        isIncluded = it.isIncluded,
                    )
                }
            }
        }

        throw GetMongCollectionsException()
    }
}
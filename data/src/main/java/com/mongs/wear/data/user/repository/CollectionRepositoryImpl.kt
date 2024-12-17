package com.mongs.wear.data.user.repository

import android.util.Log
import com.mongs.wear.data.user.api.CollectionApi
import com.mongs.wear.data.user.exception.InvalidGetMapCollectionsException
import com.mongs.wear.data.user.exception.InvalidGetMongCollectionsException
import com.mongs.wear.domain.model.CollectionModel
import com.mongs.wear.domain.repositroy.CollectionRepository
import javax.inject.Inject

class CollectionRepositoryImpl @Inject constructor(
    private val collectionApi: CollectionApi,
): CollectionRepository {

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

        throw InvalidGetMapCollectionsException()
    }

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

        throw InvalidGetMongCollectionsException()
    }
}
package com.paymong.wear.data.repository

import com.paymong.wear.data.api.client.CollectionApi
import com.paymong.wear.data.room.client.RoomDB
import com.paymong.wear.domain.error.RepositoryErrorCode
import com.paymong.wear.domain.exception.parent.ApiException
import com.paymong.wear.domain.model.CollectionModel
import com.paymong.wear.domain.repositroy.CollectionRepository
import javax.inject.Inject

class CollectionRepositoryImpl @Inject constructor(
    private val roomDB: RoomDB,
    private val collectionApi: CollectionApi,
): CollectionRepository {
    override suspend fun getMapCollections(): List<CollectionModel> {
        val res = collectionApi.findMapCollection()

        if (res.isSuccessful) {
            res.body()?.let { body ->
                return body.map { mapCollection ->
                    val mapCode = roomDB.mapCodeDao().findByCode(code = mapCollection.code)

                    CollectionModel(
                        code = mapCollection.code,
                        name = mapCode.name,
                        disable = mapCollection.disable,
                    )
                }
            }
        }

        throw ApiException(RepositoryErrorCode.FIND_MAP_COLLECTION_FAIL)
    }
    override suspend fun getMongCollections(): List<CollectionModel> {
        val res = collectionApi.findMongCollection()

        if (res.isSuccessful) {
            res.body()?.let { body ->
                return body.map { mongCollection ->
                    val mongCode = roomDB.mongCodeDao().findByCode(code = mongCollection.code)

                    CollectionModel(
                        code = mongCollection.code,
                        name = mongCode.name,
                        disable = mongCollection.disable,
                    )
                }
            }
        }

        throw ApiException(RepositoryErrorCode.FIND_MONG_COLLECTION_FAIL)
    }
}
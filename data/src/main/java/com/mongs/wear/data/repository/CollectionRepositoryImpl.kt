package com.mongs.wear.data.repository

import com.mongs.wear.data.api.client.CollectionApi
import com.mongs.wear.data.room.client.RoomDB
import com.mongs.wear.domain.error.RepositoryErrorCode
import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.model.CollectionModel
import com.mongs.wear.domain.repositroy.CollectionRepository
import javax.inject.Inject

class CollectionRepositoryImpl @Inject constructor(
    private val collectionApi: CollectionApi,
    private val roomDB: RoomDB,
): CollectionRepository {
    override suspend fun getMapCollections(): List<CollectionModel> {
        val res = collectionApi.findMapCollection()

        if (res.isSuccessful) {
            res.body()?.let { body ->
                try {
                    return body.map { mapCollection ->
                        val mapCode = roomDB.mapCodeDao().selectByCode(code = mapCollection.code)
                        CollectionModel(
                            code = mapCollection.code,
                            name = mapCode.name,
                            disable = mapCollection.disable,
                        )
                    }
                } catch (e: RuntimeException) {
                    throw RepositoryException(
                        errorCode = RepositoryErrorCode.GET_MAP_COLLECTIONS_FAIL,
                        throwable = e,
                    )
                }
            }
        }

        throw RepositoryException(RepositoryErrorCode.GET_MAP_COLLECTIONS_FAIL)
    }
    override suspend fun getMongCollections(): List<CollectionModel> {
        val res = collectionApi.findMongCollection()

        if (res.isSuccessful) {
            res.body()?.let { body ->
                try {
                    return body.map { mongCollection ->
                        val mongCode = roomDB.mongCodeDao().selectByCode(code = mongCollection.code)

                        CollectionModel(
                            code = mongCollection.code,
                            name = mongCode.name,
                            disable = mongCollection.disable,
                        )
                    }
                } catch (e: RuntimeException) {
                    throw RepositoryException(
                        errorCode = RepositoryErrorCode.GET_MONG_COLLECTIONS_FAIL,
                        throwable = e,
                    )
                }
            }
        }

        throw RepositoryException(RepositoryErrorCode.GET_MONG_COLLECTIONS_FAIL)
    }
}
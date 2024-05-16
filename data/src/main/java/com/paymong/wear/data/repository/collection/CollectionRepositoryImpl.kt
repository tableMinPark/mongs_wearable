package com.paymong.wear.data.repository.collection

import com.paymong.wear.data.api.client.CollectionApi
import com.paymong.wear.data.room.client.RoomDB
import com.paymong.wear.domain.error.CollectionErrorCode
import com.paymong.wear.domain.exception.CollectionException
import com.paymong.wear.domain.repository.collection.CollectionRepository
import com.paymong.wear.domain.refac.vo.CollectionVo
import javax.inject.Inject

class CollectionRepositoryImpl @Inject constructor(
    private val collectionApi: CollectionApi,
    private val roomDB: RoomDB,
) : CollectionRepository {
    override suspend fun getAllCollectionMap(): List<CollectionVo> {
        val response = collectionApi.findMapCollection()

        if (response.isSuccessful) {
            response.body()?.let { findMapCollectionResDtoList ->
                val mapCollectionVoList = ArrayList<CollectionVo>()
                val mapCodeDao = roomDB.mapCodeDao()

                findMapCollectionResDtoList.forEach { findMapCollectionResDto ->
                    val code = findMapCollectionResDto.code
                    val name = mapCodeDao.findByCode(code).name
                    val disable = findMapCollectionResDto.disable
                    mapCollectionVoList.add(CollectionVo(code = code, name = name,disable = disable))
                }
                return mapCollectionVoList;
            }
        }

        throw CollectionException(CollectionErrorCode.GET_COLLECTION_MAP_FAIL)
    }
    override suspend fun getAllCollectionMong(): List<CollectionVo> {
        val response = collectionApi.findMongCollection()

        if (response.isSuccessful) {
            val mongCollectionVoList = ArrayList<CollectionVo>()

            response.body()?.let { findMongCollectionResDtoList ->
                val mongCodeDao = roomDB.mongCodeDao()

                findMongCollectionResDtoList.forEach { findMongCollectionResDto ->
                    val code = findMongCollectionResDto.code
                    val name = mongCodeDao.findByCode(code).name
                    val disable = findMongCollectionResDto.disable
                    mongCollectionVoList.add(CollectionVo(code = code, name = name,disable = disable))
                }

                return mongCollectionVoList;
            }
        }

        throw CollectionException(CollectionErrorCode.GET_COLLECTION_MONG_FAIL)
    }
}
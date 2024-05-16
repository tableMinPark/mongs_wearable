package com.paymong.wear.data.repository_.collection

import com.paymong.wear.data.api.client.CollectionApi
import com.paymong.wear.data.room.client.RoomDB
import com.paymong.wear.domain.error.CollectionErrorCode
import com.paymong.wear.domain.exception.CollectionException
import com.paymong.wear.domain.repository.collection.CollectionRepository
import com.paymong.wear.domain.vo.MongCollectionVo
import javax.inject.Inject

class CollectionRepositoryImpl @Inject constructor(
    private val collectionApi: CollectionApi,
    private val roomDB: RoomDB,
) : CollectionRepository {
    override suspend fun getAllCollectionMap(): List<MongCollectionVo> {
        val response = collectionApi.findMapCollection()

        if (response.isSuccessful) {
            response.body()?.let { findMapCollectionResDtoList ->
                val mapMongCollectionVoList = ArrayList<MongCollectionVo>()
                val mapCodeDao = roomDB.mapCodeDao()

                findMapCollectionResDtoList.forEach { findMapCollectionResDto ->
                    val code = findMapCollectionResDto.code
                    val name = mapCodeDao.findByCode(code).name
                    val disable = findMapCollectionResDto.disable
                    mapMongCollectionVoList.add(MongCollectionVo(code = code, name = name,disable = disable))
                }
                return mapMongCollectionVoList;
            }
        }

        throw CollectionException(CollectionErrorCode.GET_COLLECTION_MAP_FAIL)
    }
    override suspend fun getAllCollectionMong(): List<MongCollectionVo> {
        val response = collectionApi.findMongCollection()

        if (response.isSuccessful) {
            val mongMongCollectionVoList = ArrayList<MongCollectionVo>()

            response.body()?.let { findMongCollectionResDtoList ->
                val mongCodeDao = roomDB.mongCodeDao()

                findMongCollectionResDtoList.forEach { findMongCollectionResDto ->
                    val code = findMongCollectionResDto.code
                    val name = mongCodeDao.findByCode(code).name
                    val disable = findMongCollectionResDto.disable
                    mongMongCollectionVoList.add(MongCollectionVo(code = code, name = name,disable = disable))
                }

                return mongMongCollectionVoList;
            }
        }

        throw CollectionException(CollectionErrorCode.GET_COLLECTION_MONG_FAIL)
    }
}
package com.mongs.wear.data.manager.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.room.Transaction
import com.mongs.wear.data.global.room.RoomDB
import com.mongs.wear.data.manager.api.ManagementApi
import com.mongs.wear.data.manager.entity.MongEntity
import com.mongs.wear.domain.management.model.MongModel
import com.mongs.wear.domain.management.repository.SlotRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SlotRepositoryImpl @Inject constructor(
    private val roomDB: RoomDB,
    private val managementApi: ManagementApi,
): SlotRepository {

    /**
     * 현재 몽 선택
     */
    override suspend fun setCurrentSlot(mongId: Long) {

        roomDB.mongDao().let { dao ->
            // 전체 선택 해제 (오류 값 보정)
            dao.findAllByIsCurrentTrue().map { mongEntity ->
                dao.save(mongEntity.update(isCurrent = false))
            }

            dao.findByMongId(mongId = mongId)?.let { mongEntity ->
                dao.save(mongEntity.update(isCurrent = true))
            }
        }
    }

    /**
     * 현재 선택된 몽 조회
     */
    override suspend fun getCurrentSlot(): MongModel? {

        return roomDB.mongDao().findByIsCurrentTrue()?.let { currentMongEntity ->
            roomDB.mongDao().findByMongId(mongId = currentMongEntity.mongId)
                ?.toMongModel()

        } ?: run {
            null
        }
    }

    /**
     * 현재 선택된 몽 Live 조회
     */
    override suspend fun getCurrentSlotLive(): LiveData<MongModel?> {

        roomDB.mongDao().let { dao ->
            return dao.findByIsCurrentTrue()?.let { currentMongEntity ->
                dao.findLiveByMongId(mongId = currentMongEntity.mongId).map { mongEntity ->
                    mongEntity?.toMongModel() ?: run { null }
                }
            } ?: MutableLiveData(null)
        }
    }

    /**
     * 현재 선택된 몽 정보 동기화
     */
    @Transaction
    override suspend fun updateCurrentSlot() {
        roomDB.mongDao().findByIsCurrentTrue()?.let { mongEntity ->

            val response = managementApi.getMong(mongId = mongEntity.mongId)

            if (response.isSuccessful) {
                response.body()?.let { body ->
                    roomDB.mongDao().let { dao ->
                        dao.findByMongId(mongId = mongEntity.mongId)?.let { mongEntity ->
                            dao.save(
                                mongEntity.update(
                                    mongName = body.result.mongName,
                                    mongTypeCode = body.result.mongTypeCode,
                                    payPoint = body.result.payPoint,
                                    stateCode = body.result.stateCode,
                                    isSleeping = body.result.isSleep,
                                    statusCode = body.result.statusCode,
                                    expRatio = body.result.expRatio,
                                    weight = body.result.weight,
                                    healthyRatio = body.result.healthyRatio,
                                    satietyRatio = body.result.satietyRatio,
                                    strengthRatio = body.result.strengthRatio,
                                    fatigueRatio = body.result.fatigueRatio,
                                    poopCount = body.result.poopCount,
                                    updatedAt = body.result.updatedAt,
                                )
                            ).toMongModel()
                        } ?: run {
                            dao.save(
                                MongEntity(
                                    mongId = body.result.mongId,
                                    mongName = body.result.mongName,
                                    mongTypeCode = body.result.mongTypeCode,
                                    payPoint = body.result.payPoint,
                                    stateCode = body.result.stateCode,
                                    isSleeping = body.result.isSleep,
                                    statusCode = body.result.statusCode,
                                    expRatio = body.result.expRatio,
                                    weight = body.result.weight,
                                    healthyRatio = body.result.healthyRatio,
                                    satietyRatio = body.result.satietyRatio,
                                    strengthRatio = body.result.strengthRatio,
                                    fatigueRatio = body.result.fatigueRatio,
                                    poopCount = body.result.poopCount,
                                    createdAt = body.result.createdAt,
                                    updatedAt = body.result.updatedAt,
                                )
                            ).toMongModel()
                        }
                    }
                }
            } else {
                roomDB.mongDao().deleteByMongId(mongId = mongEntity.mongId)
            }
        }
    }
}
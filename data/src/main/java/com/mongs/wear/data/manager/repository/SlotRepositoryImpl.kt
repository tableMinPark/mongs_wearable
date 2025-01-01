package com.mongs.wear.data.manager.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.mongs.wear.data.common.room.RoomDB
import com.mongs.wear.data.manager.api.ManagementApi
import com.mongs.wear.data.manager.exception.GetMongException
import com.mongs.wear.domain.management.model.MongModel
import com.mongs.wear.domain.management.repository.SlotRepository
import javax.inject.Inject

class SlotRepositoryImpl @Inject constructor(
    private val roomDB: RoomDB,
    private val managementApi: ManagementApi,
): SlotRepository {

    override suspend fun setCurrentSlot(mongId: Long) {

        this.updateCurrentSlot(mongId = mongId)

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
     * 현재 선택된 몽 정보 업데이트
     */
    private suspend fun updateCurrentSlot(mongId: Long) {

        roomDB.mongDao().findByMongId(mongId = mongId)?.let { mongEntity ->

            val response = managementApi.getMong(mongId = mongEntity.mongId)

            if (response.isSuccessful) {
                response.body()?.let { body ->
                    roomDB.mongDao().save(
                        mongEntity.update(
                            mongTypeCode = body.result.mongTypeCode,
                            payPoint = body.result.payPoint,
                            weight = body.result.weight,
                            expRatio = body.result.expRatio,
                            healthyRatio = body.result.healthyRatio,
                            satietyRatio = body.result.satietyRatio,
                            strengthRatio = body.result.strengthRatio,
                            fatigueRatio = body.result.fatigueRatio,
                            poopCount = body.result.poopCount,
                            stateCode = body.result.stateCode,
                            statusCode = body.result.statusCode,
                            isSleeping = body.result.isSleep,
                        )
                    )
                }
            } else {
                throw GetMongException(mongId = mongEntity.mongId)
            }
        }
    }

    /**
     * 현재 선택된 몽 조회
     */
    override suspend fun getCurrentSlot(): MongModel? {

        return roomDB.mongDao().findByIsCurrentTrue()?.let { mongEntity ->

            this.updateCurrentSlot(mongId = mongEntity.mongId)

            roomDB.mongDao().findByMongId(mongId = mongEntity.mongId)?.toMongModel()

        } ?: run {
            null
        }
    }

    override suspend fun getCurrentSlotLive(): LiveData<MongModel?> {

        roomDB.mongDao().findByIsCurrentTrue()?.let { mongEntity ->
            this.updateCurrentSlot(mongId = mongEntity.mongId)
        }

        return roomDB.mongDao().findLiveByIsCurrentTrue().map { mongEntity ->
            mongEntity?.toMongModel() ?: run { null }
        }
    }
}
package com.mongs.wear.data.manager.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.mongs.wear.data.common.room.RoomDB
import com.mongs.wear.data.manager.api.ManagementApi
import com.mongs.wear.data.manager.exception.GetMongException
import com.mongs.wear.domain.slot.model.SlotModel
import com.mongs.wear.domain.slot.repository.SlotRepository
import javax.inject.Inject

class SlotRepositoryImpl @Inject constructor(
    private val roomDB: RoomDB,
    private val managementApi: ManagementApi,
): SlotRepository {

    private suspend fun updateCurrentSlot(mongId: Long) {

        roomDB.mongDao().findByMongId(mongId = mongId)?.let { mongEntity ->

            val response = managementApi.getMong(mongId = mongEntity.mongId)

            if (!response.isSuccessful) {

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

    override suspend fun getCurrentSlot(): SlotModel? = roomDB.mongDao().findByIsCurrentTrue()?.let { mongEntity ->

        SlotModel(
            mongId = mongEntity.mongId,
            mongName = mongEntity.mongName,
            payPoint = mongEntity.payPoint,
            mongTypeCode = mongEntity.mongTypeCode,
            createdAt = mongEntity.createdAt,
            weight = mongEntity.weight,
            expRatio = mongEntity.expRatio,
            healthyRatio = mongEntity.healthyRatio,
            satietyRatio = mongEntity.satietyRatio,
            strengthRatio = mongEntity.strengthRatio,
            fatigueRatio = mongEntity.fatigueRatio,
            poopCount = mongEntity.poopCount,
            stateCode = mongEntity.stateCode,
            statusCode = mongEntity.statusCode,
            isSleeping = mongEntity.isSleeping,
            isCurrent = mongEntity.isCurrent,
            graduateCheck = mongEntity.graduateCheck,
            isHappy = mongEntity.isHappy,
            isEating = mongEntity.isEating,
            isPoopCleaning = mongEntity.isPoopCleaning,
        )
    } ?: run { null }


    override suspend fun getCurrentSlotLive(): LiveData<SlotModel?> = roomDB.mongDao().findLiveByIsCurrentTrue().map { mongEntity ->

        mongEntity?.let {

            SlotModel(
                mongId = mongEntity.mongId,
                mongName = mongEntity.mongName,
                payPoint = mongEntity.payPoint,
                mongTypeCode = mongEntity.mongTypeCode,
                createdAt = mongEntity.createdAt,
                weight = mongEntity.weight,
                expRatio = mongEntity.expRatio,
                healthyRatio = mongEntity.healthyRatio,
                satietyRatio = mongEntity.satietyRatio,
                strengthRatio = mongEntity.strengthRatio,
                fatigueRatio = mongEntity.fatigueRatio,
                poopCount = mongEntity.poopCount,
                stateCode = mongEntity.stateCode,
                statusCode = mongEntity.statusCode,
                isSleeping = mongEntity.isSleeping,
                isCurrent = mongEntity.isCurrent,
                graduateCheck = mongEntity.graduateCheck,
                isHappy = mongEntity.isHappy,
                isEating = mongEntity.isEating,
                isPoopCleaning = mongEntity.isPoopCleaning,
            )
        } ?: run { null }
    }

    override suspend fun getSlotsLive(): LiveData<List<SlotModel>> {

        val response = managementApi.getMongs()

        if (response.isSuccessful) {

            response.body()?.let { body ->

                body.result.forEach({ getMongResponseDto ->

                    roomDB.mongDao().let { dao ->

                        dao.findByMongId(getMongResponseDto.mongId)?.let { mongEntity ->

                            dao.save(
                                mongEntity.update(
                                    mongTypeCode = getMongResponseDto.mongTypeCode,
                                    payPoint = getMongResponseDto.payPoint,
                                    weight = getMongResponseDto.weight,
                                    expRatio = getMongResponseDto.expRatio,
                                    healthyRatio = getMongResponseDto.healthyRatio,
                                    satietyRatio = getMongResponseDto.satietyRatio,
                                    strengthRatio = getMongResponseDto.strengthRatio,
                                    fatigueRatio = getMongResponseDto.fatigueRatio,
                                    poopCount = getMongResponseDto.poopCount,
                                    stateCode = getMongResponseDto.stateCode,
                                    statusCode = getMongResponseDto.statusCode,
                                    isSleeping = getMongResponseDto.isSleep,
                                )
                            )
                        }
                    }
                })
            }
        }

        return roomDB.mongDao().findLiveAll().map { mongEntities ->
            mongEntities.map { mongEntity ->
                SlotModel(
                    mongId = mongEntity.mongId,
                    mongName = mongEntity.mongName,
                    payPoint = mongEntity.payPoint,
                    mongTypeCode = mongEntity.mongTypeCode,
                    createdAt = mongEntity.createdAt,
                    weight = mongEntity.weight,
                    expRatio = mongEntity.expRatio,
                    healthyRatio = mongEntity.healthyRatio,
                    satietyRatio = mongEntity.satietyRatio,
                    strengthRatio = mongEntity.strengthRatio,
                    fatigueRatio = mongEntity.fatigueRatio,
                    poopCount = mongEntity.poopCount,
                    stateCode = mongEntity.stateCode,
                    statusCode = mongEntity.statusCode,
                    isSleeping = mongEntity.isSleeping,
                    isCurrent = mongEntity.isCurrent,
                    graduateCheck = mongEntity.graduateCheck,
                    isHappy = mongEntity.isHappy,
                    isEating = mongEntity.isEating,
                    isPoopCleaning = mongEntity.isPoopCleaning,
                )
            }
        }
    }

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
}
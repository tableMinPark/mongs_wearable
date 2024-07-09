package com.mongs.wear.data.repository

import com.mongs.wear.data.api.client.ManagementApi
import com.mongs.wear.data.code.SlotShift
import com.mongs.wear.data.code.SlotState
import com.mongs.wear.data.dto.management.req.FeedMongReqDto
import com.mongs.wear.data.dto.management.req.RegisterMongReqDto
import com.mongs.wear.data.room.client.RoomDB
import com.mongs.wear.data.room.entity.Slot
import com.mongs.wear.domain.code.ShiftCode
import com.mongs.wear.domain.error.RepositoryErrorCode
import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.model.FeedLogModel
import com.mongs.wear.domain.repositroy.ManagementRepository
import javax.inject.Inject

class ManagementRepositoryImpl @Inject constructor(
    private val managementApi: ManagementApi,
    private val roomDB: RoomDB,
): ManagementRepository {
    override suspend fun addMong(name: String, sleepStart: String, sleepEnd: String) {
        val res = managementApi.registerMong(
            RegisterMongReqDto(
                name = name,
                sleepStart = sleepStart,
                sleepEnd = sleepEnd,
            )
        )

        if (res.isSuccessful) {
            res.body()?.let { body ->
                try {
                    roomDB.slotDao().insert(
                        Slot(
                            mongId = body.mongId,
                            name = body.name,
                            mongCode = body.mongCode,
                            weight = body.weight,
                            healthy = body.healthy,
                            satiety = body.satiety,
                            strength = body.strength,
                            sleep = body.sleep,
                            poopCount = body.poopCount,
                            isSleeping = body.isSleeping,
                            exp = body.exp,
                            stateCode = body.stateCode,
                            shiftCode = body.shiftCode,
                            payPoint = body.payPoint,
                            born = body.born,
                        )
                    )
                } catch (e: RuntimeException) {
                    throw RepositoryException(
                        errorCode = RepositoryErrorCode.ADD_MONG_FAIL,
                        throwable = e,
                    )
                }
            }
        } else {
            throw RepositoryException(RepositoryErrorCode.ADD_MONG_FAIL)
        }
    }
    override suspend fun deleteMong(mongId: Long) {
        val res = managementApi.deleteMong(mongId = mongId)

        if (res.isSuccessful) {
            res.body()?.let { body ->
                try {
                    roomDB.slotDao().deleteByMongId(mongId = body.mongId)
                } catch (e: RuntimeException) {
                    throw RepositoryException(
                        errorCode = RepositoryErrorCode.DELETE_MONG_FAIL,
                        throwable = e,
                    )
                }
            }
        } else {
            throw RepositoryException(RepositoryErrorCode.DELETE_MONG_FAIL)
        }
    }
    override suspend fun getFeedLog(mongId: Long): List<FeedLogModel> {
        val res = managementApi.findFeedLog(mongId = mongId)

        if (res.isSuccessful) {
            res.body()?.let { body ->
                return body.map { feedLog ->
                    FeedLogModel(
                        code = feedLog.code,
                        isCanBuy = feedLog.isCanBuy,
                    )
                }
            }
        }

        throw RepositoryException(RepositoryErrorCode.GET_FEED_LOG_FAIL)
    }
    override suspend fun feedMong(mongId: Long, code: String) {
        val res = managementApi.feedMong(
            mongId = mongId,
            feedMongReqDto = FeedMongReqDto(foodCode = code)
        )

        if (res.isSuccessful) {
            res.body()?.let { body ->
                try {
                    roomDB.slotDao().updateByFeedMong(
                        mongId = body.mongId,
                        weight = body.weight,
                        strength = body.strength,
                        satiety = body.satiety,
                        healthy = body.healthy,
                        sleep = body.sleep,
                        exp = body.exp,
                        payPoint = body.payPoint,
                    )
                } catch (e: RuntimeException) {
                    throw RepositoryException(
                        errorCode = RepositoryErrorCode.FEED_MONG_FAIL,
                        throwable = e,
                    )
                }
            }
        } else {
            throw RepositoryException(RepositoryErrorCode.FEED_MONG_FAIL)
        }
    }
    override suspend fun graduateMong(mongId: Long) {
        val res = managementApi.graduateMong(mongId = mongId)

        if (res.isSuccessful) {
            res.body()?.let { body ->
                try {
                    roomDB.slotDao().deleteByMongId(mongId = body.mongId)
                } catch (e: RuntimeException) {
                    throw RepositoryException(
                        errorCode = RepositoryErrorCode.GRADUATE_MONG_FAIL,
                        throwable = e,
                    )
                }
            }
        } else {
            throw RepositoryException(RepositoryErrorCode.GRADUATE_MONG_FAIL)
        }
    }
    override suspend fun graduateReadyMong(mongId: Long) {
        try {
            roomDB.slotDao().updateByGraduateReadyMong(mongId = mongId)
        } catch (e: RuntimeException) {
            throw RepositoryException(
                errorCode = RepositoryErrorCode.GRADUATE_MONG_READY_FAIL,
                throwable = e,
            )
        }
    }
    override suspend fun evolutionMong(mongId: Long) {
        val res = managementApi.evolutionMong(mongId = mongId)

        if (res.isSuccessful) {
            res.body()?.let { body ->
                try {
                    roomDB.slotDao().updateByEvolutionMong(
                        mongId = body.mongId,
                        mongCode = body.mongCode,
                        shiftCode = SlotShift.valueOf(body.shiftCode).code,
                        stateCode = SlotState.valueOf(body.stateCode).code,
                        weight = body.weight,
                        strength = body.strength,
                        satiety = body.satiety,
                        healthy = body.healthy,
                        sleep = body.sleep,
                        exp = body.exp,
                    )
                } catch (e: RuntimeException) {
                    throw RepositoryException(
                        errorCode = RepositoryErrorCode.EVOLUTION_MONG_FAIL,
                        throwable = e,
                    )
                }
            }
        } else {
            throw RepositoryException(RepositoryErrorCode.EVOLUTION_MONG_FAIL)
        }
    }
    override suspend fun sleepingMong(mongId: Long) {
        val res = managementApi.sleepingMong(mongId = mongId)

        if (res.isSuccessful) {
            res.body()?.let { body ->
                try {
                    roomDB.slotDao().updateBySleepingMong(
                        mongId = body.mongId,
                        isSleeping = body.isSleeping,
                    )
                } catch (e: RuntimeException) {
                    throw RepositoryException(
                        errorCode = RepositoryErrorCode.SLEEPING_MONG_FAIL,
                        throwable = e,
                    )
                }
            }
        } else {
            throw RepositoryException(RepositoryErrorCode.SLEEPING_MONG_FAIL)
        }
    }
    override suspend fun strokeMong(mongId: Long) {
        val res = managementApi.strokeMong(mongId = mongId)

        if (res.isSuccessful) {
            res.body()?.let { body ->
                try {
                    roomDB.slotDao().updateByStrokeMong(
                        mongId = mongId,
                        exp = body.exp,
                    )
                } catch (e: RuntimeException) {
                    throw RepositoryException(
                        errorCode = RepositoryErrorCode.STROKE_MONG_FAIL,
                        throwable = e,
                    )
                }
            }
        } else {
            throw RepositoryException(RepositoryErrorCode.STROKE_MONG_FAIL)
        }
    }
    override suspend fun poopCleanMong(mongId: Long) {
        val res = managementApi.poopCleanMong(mongId = mongId)

        if (res.isSuccessful) {
            res.body()?.let { body ->
                try {
                    roomDB.slotDao().updateByPoopCleanMong(
                        mongId = body.mongId,
                        poopCount = body.poopCount,
                        exp = body.exp,
                    )
                } catch (e: RuntimeException) {
                    throw RepositoryException(
                        errorCode = RepositoryErrorCode.POOP_CLEAN_MONG_FAIL,
                        throwable = e,
                    )
                }
            }
        } else {
            throw RepositoryException(RepositoryErrorCode.POOP_CLEAN_MONG_FAIL)
        }
    }
    override suspend fun setIsHappy(mongId: Long, isHappy: Boolean) {
        try {
            roomDB.slotDao().updateIsHappyByStrokeMong(mongId = mongId, isHappy = isHappy)
        } catch (e: RuntimeException) {
            throw RepositoryException(
                errorCode = RepositoryErrorCode.SET_IS_HAPPY_FAIL,
                throwable = e,
            )
        }
    }
    override suspend fun setIsEating(mongId: Long, isEating: Boolean) {
        try {
            roomDB.slotDao().updateIsEatingByFeedMong(mongId = mongId, isEating = isEating)
        } catch (e: RuntimeException) {
            throw RepositoryException(
                errorCode = RepositoryErrorCode.SET_IS_EATING_FAIL,
                throwable = e,
            )
        }
    }

    override suspend fun setIsPoopCleaning(mongId: Long, isPoopCleaning: Boolean) {
        try {
            roomDB.slotDao().updateIsPoopCleaningByPoopClean(mongId = mongId, isPoopCleaning = isPoopCleaning)
        } catch (e: RuntimeException) {
            throw RepositoryException(
                errorCode = RepositoryErrorCode.SET_IS_POOP_CLEANING_FAIL,
                throwable = e,
            )
        }
    }
}
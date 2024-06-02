package com.paymong.wear.data.repository

import com.paymong.wear.data.api.client.ManagementApi
import com.paymong.wear.data.code.Shift
import com.paymong.wear.data.code.State
import com.paymong.wear.data.dto.management.request.FeedMongReqDto
import com.paymong.wear.data.dto.management.request.RegisterMongReqDto
import com.paymong.wear.data.room.client.RoomDB
import com.paymong.wear.data.room.entity.Slot
import com.paymong.wear.domain.error.RepositoryErrorCode
import com.paymong.wear.domain.exception.parent.RepositoryException
import com.paymong.wear.domain.model.FeedLogModel
import com.paymong.wear.domain.repositroy.ManagementRepository
import javax.inject.Inject

class ManagementRepositoryImpl @Inject constructor(
    private val roomDB: RoomDB,
    private val managementApi: ManagementApi,
): ManagementRepository {
    override suspend fun add(name: String, sleepStart: String, sleepEnd: String) {
        val res = managementApi.registerMong(
            RegisterMongReqDto(
                name = name,
                sleepStart = sleepStart,
                sleepEnd = sleepEnd,
            )
        )

        if (res.isSuccessful) {
            res.body()?.let { body ->
                if (roomDB.slotDao().countByMongId(mongId = body.mongId) == 0) {
                    roomDB.slotDao().register(
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
                }
            }
        } else {
            throw RepositoryException(RepositoryErrorCode.ADD_MONG_FAIL)
        }
    }
    override suspend fun delete(mongId: Long) {
        val res = managementApi.deleteMong(mongId = mongId)

        if (res.isSuccessful) {
            res.body()?.let { body ->
                roomDB.slotDao().deleteByMongId(mongId = body.mongId)
            }
        } else {
            throw RepositoryException(RepositoryErrorCode.DELETE_MONG_FAIL)
        }
    }
    override suspend fun getFeedLog(mongId: Long): List<FeedLogModel> {
        val res = managementApi.findFeedLog(mongId = mongId)

        if (res.isSuccessful) {
            res.body()?.let { body ->
                body.map { feedLog ->
                    FeedLogModel(
                        code = feedLog.code,
                        isCanBuy = feedLog.isCanBuy,
                    )
                }
            }
        }

        throw RepositoryException(RepositoryErrorCode.NOT_FOUND_FEED_LOG)
    }
    override suspend fun feed(mongId: Long, code: String) {
        val res = managementApi.feedMong(
            mongId = mongId,
            feedMongReqDto = FeedMongReqDto(foodCode = code)
        )

        if (res.isSuccessful) {
            res.body()?.let { body ->
                roomDB.slotDao().modifyAfterFeed(
                    mongId = body.mongId,
                    weight = body.weight,
                    strength = body.strength,
                    satiety = body.satiety,
                    healthy = body.healthy,
                    sleep = body.sleep,
                    exp = body.exp,
                    payPoint = body.payPoint,
                )
            }
        } else {
            throw RepositoryException(RepositoryErrorCode.FEED_MONG_FAIL)
        }
    }
    override suspend fun graduate(mongId: Long) {
        val res = managementApi.graduateMong(mongId = mongId)

        if (res.isSuccessful) {
            res.body()?.let { body ->
                roomDB.slotDao().deleteByMongId(mongId = body.mongId)
            }
        } else {
            throw RepositoryException(RepositoryErrorCode.GRADUATE_MONG_FAIL)
        }
    }
    override suspend fun evolution(mongId: Long) {
        val res = managementApi.evolutionMong(mongId = mongId)

        if (res.isSuccessful) {
            res.body()?.let { body ->
                roomDB.slotDao().modifyAfterEvolution(
                    mongId = body.mongId,
                    mongCode = body.mongCode,
                    weight = body.weight,
                    strength = body.strength,
                    satiety = body.satiety,
                    healthy = body.healthy,
                    sleep = body.sleep,
                    shiftCode = Shift.valueOf(body.shiftCode).code,
                    stateCode = State.valueOf(body.stateCode).code,
                    exp = body.exp,
                )
            }
        } else {
            throw RepositoryException(RepositoryErrorCode.EVOLUTION_MONG_FAIL)
        }
    }
    override suspend fun sleeping(mongId: Long) {
        val res = managementApi.sleepingMong(mongId = mongId)

        if (res.isSuccessful) {
            res.body()?.let { body ->
                roomDB.slotDao().modifyAfterSleeping(
                    mongId = body.mongId,
                    isSleeping = body.isSleeping,
                )
            }
        } else {
            throw RepositoryException(RepositoryErrorCode.SLEEPING_MONG_FAIL)
        }
    }
    override suspend fun stroke(mongId: Long) {
        val res = managementApi.strokeMong(mongId = mongId)

        if (res.isSuccessful) {
            res.body()?.let { body ->
                roomDB.slotDao().modifyAfterStroke(
                    mongId = body.mongId,
                    exp = body.exp,
                )
            }
        } else {
            throw RepositoryException(RepositoryErrorCode.STROKE_MONG_FAIL)
        }
    }
    override suspend fun poopClean(mongId: Long) {
        val res = managementApi.poopCleanMong(mongId = mongId)

        if (res.isSuccessful) {
            res.body()?.let { body ->
                roomDB.slotDao().modifyAfterPoopClean(
                    mongId = body.mongId,
                    poopCount = body.poopCount,
                    exp = body.exp,
                )
            }
        } else {
            throw RepositoryException(RepositoryErrorCode.POOP_CLEAN_FAIL)
        }
    }
}
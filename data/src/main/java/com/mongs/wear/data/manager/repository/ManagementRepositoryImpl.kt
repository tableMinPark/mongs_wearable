package com.mongs.wear.data.manager.repository

import com.mongs.wear.data.common.room.RoomDB
import com.mongs.wear.data.manager.api.ManagementApi
import com.mongs.wear.data.manager.dto.request.CreateMongRequestDto
import com.mongs.wear.data.manager.dto.request.FeedMongRequestDto
import com.mongs.wear.data.manager.entity.MongEntity
import com.mongs.wear.data.manager.exception.CreateMongException
import com.mongs.wear.data.manager.exception.DeleteMongException
import com.mongs.wear.data.manager.exception.EvolutionMongException
import com.mongs.wear.data.manager.exception.FeedMongException
import com.mongs.wear.data.manager.exception.GetFeedItemsException
import com.mongs.wear.data.manager.exception.GraduateMongException
import com.mongs.wear.data.manager.exception.PoopCleanMongException
import com.mongs.wear.data.manager.exception.SleepMongException
import com.mongs.wear.data.manager.exception.StrokeMongException
import com.mongs.wear.domain.management.model.FeedItemModel
import com.mongs.wear.domain.management.repository.ManagementRepository
import com.mongs.wear.domain.management.model.MongModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

class ManagementRepositoryImpl @Inject constructor(
    private val roomDB: RoomDB,
    private val managementApi: ManagementApi,
): ManagementRepository {

    companion object {
        private const val EFFECT_DELAY = 2 * 1000L
    }

    override suspend fun getMongs(): List<MongModel> {

        val response = managementApi.getMongs()

        if (response.isSuccessful) {
            response.body()?.let { body ->

                roomDB.mongDao().let { dao ->
                    // 없는 Mong 삭제
                    body.result.let { getMongResponseDtos ->
                        dao.deleteByMongIdNotIn(getMongResponseDtos.map { getMongResponseDto -> getMongResponseDto.mongId })
                    }

                    body.result.forEach({ getMongResponseDto ->
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
                        } ?: run {
                            dao.save(
                                MongEntity(
                                    mongId = getMongResponseDto.mongId,
                                    mongName = getMongResponseDto.mongName,
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
                                    createdAt = getMongResponseDto.createdAt,
                                )
                            )
                        }
                    })
                }
            }
        }

        return roomDB.mongDao().findAll().map { mongEntity ->
            MongModel(
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

    override suspend fun createMong(name: String, sleepStart: String, sleepEnd: String) {

        val response = managementApi.createMong(createMongRequestDto = CreateMongRequestDto(
            name = name,
            sleepAt = LocalTime.parse(sleepStart),
            wakeupAt = LocalTime.parse(sleepEnd),
        ));

        if (!response.isSuccessful) {
            throw CreateMongException(name = name, sleepStart = sleepStart, sleepEnd = sleepEnd)
        }
    }

    override suspend fun deleteMong(mongId: Long) {

        val response = managementApi.deleteMong(mongId = mongId)

        if (!response.isSuccessful) {
            throw DeleteMongException(mongId = mongId)
        } else {
            roomDB.mongDao().deleteByMongId(mongId = mongId)
        }
    }

    override suspend fun getFeedItems(mongId: Long, foodTypeGroupCode: String): List<FeedItemModel> {

        val response = managementApi.getFeedItems(mongId = mongId, foodTypeGroupCode = foodTypeGroupCode)

        if (response.isSuccessful) {
            response.body()?.let { body ->

                return body.result.feedItems.map {
                    FeedItemModel(
                        foodTypeCode = it.foodTypeCode,
                        foodTypeGroupCode = it.foodTypeGroupCode,
                        foodTypeName = it.foodTypeName,
                        price = it.price,
                        isCanBuy = it.isCanBuy,
                        addWeightValue = it.addWeightValue,
                        addStrengthValue = it.addStrengthValue,
                        addSatietyValue = it.addSatietyValue,
                        addHealthyValue = it.addHealthyValue,
                        addFatigueValue = it.addFatigueValue,
                    )
                }
            }
        }

        throw GetFeedItemsException(mongId = mongId)
    }

    override suspend fun feedMong(mongId: Long, foodTypeCode: String) {

        val response = managementApi.feedMong(mongId = mongId, feedMongRequestDto = FeedMongRequestDto(foodTypeCode = foodTypeCode))

        if (!response.isSuccessful) {
            throw FeedMongException(mongId = mongId, foodTypeCode = foodTypeCode)
        }
    }

    override suspend fun graduateMong(mongId: Long) {

        val response = managementApi.graduateMong(mongId = mongId)

        if (!response.isSuccessful) {
            throw GraduateMongException(mongId = mongId)
        }
    }

    override suspend fun graduateCheckMong(mongId: Long) {

        roomDB.mongDao().findByMongId(mongId = mongId)?.let { mongEntity ->

            mongEntity.graduateCheck()

            roomDB.mongDao().save(mongEntity)
        }
    }

    override suspend fun evolutionMong(mongId: Long) {

        val response = managementApi.evolutionMong(mongId = mongId)

        if (!response.isSuccessful) {
            throw EvolutionMongException(mongId = mongId)
        } else {
            roomDB.mongDao().let{ dao ->
                dao.findByMongId(mongId = mongId)?.let { mongEntity ->
                    dao.save(mongEntity.update(

                    ))
                }
            }
        }
    }

    override suspend fun sleepingMong(mongId: Long) {

        val response = managementApi.sleepMong(mongId = mongId)

        if (!response.isSuccessful) {
            throw SleepMongException(mongId = mongId)
        }
    }

    override suspend fun strokeMong(mongId: Long) {

        val response = managementApi.strokeMong(mongId = mongId)

        if (!response.isSuccessful) {
            throw StrokeMongException(mongId = mongId)
        }
    }

    override suspend fun poopCleanMong(mongId: Long) {

        val response = managementApi.poopCleanMong(mongId = mongId)

        if (!response.isSuccessful) {
            throw PoopCleanMongException(mongId = mongId)
        }
    }

    override suspend fun setIsHappy(mongId: Long) {

        roomDB.mongDao().findByMongId(mongId = mongId)?.let { mongEntity ->

            CoroutineScope(Dispatchers.IO).launch {
                mongEntity.happy()
                roomDB.mongDao().save(mongEntity)

                delay(EFFECT_DELAY)

                mongEntity.resetFlag()
                roomDB.mongDao().save(mongEntity)
            }
        }
    }

    override suspend fun setIsEating(mongId: Long) {

        roomDB.mongDao().findByMongId(mongId = mongId)?.let { mongEntity ->

            CoroutineScope(Dispatchers.IO).launch {
                mongEntity.eat()
                roomDB.mongDao().save(mongEntity)

                delay(EFFECT_DELAY)

                mongEntity.resetFlag()
                roomDB.mongDao().save(mongEntity)
            }
        }
    }

    override suspend fun setIsPoopCleaning(mongId: Long) {

        roomDB.mongDao().findByMongId(mongId = mongId)?.let { mongEntity ->

            CoroutineScope(Dispatchers.IO).launch {
                mongEntity.poopClean()
                roomDB.mongDao().save(mongEntity)

                delay(EFFECT_DELAY)

                mongEntity.resetFlag()
                roomDB.mongDao().save(mongEntity)
            }
        }
    }
}
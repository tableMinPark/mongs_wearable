package com.mongs.wear.data.manager.repository

import com.mongs.wear.data.common.room.RoomDB
import com.mongs.wear.data.manager.api.ManagementApi
import com.mongs.wear.data.manager.dto.request.CreateMongRequestDto
import com.mongs.wear.data.manager.dto.request.FeedMongRequestDto
import com.mongs.wear.data.manager.exception.InvalidCreateMongException
import com.mongs.wear.data.manager.exception.InvalidDeleteMongException
import com.mongs.wear.data.manager.exception.InvalidEvolutionMongException
import com.mongs.wear.data.manager.exception.InvalidFeedMongException
import com.mongs.wear.data.manager.exception.InvalidGetFeedItemsException
import com.mongs.wear.data.manager.exception.InvalidGraduateMongException
import com.mongs.wear.data.manager.exception.InvalidPoopCleanMongException
import com.mongs.wear.data.manager.exception.InvalidSleepMongException
import com.mongs.wear.data.manager.exception.InvalidStrokeMongException
import com.mongs.wear.domain.management.model.FeedItemModel
import com.mongs.wear.domain.management.repository.ManagementRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

class ManagementRepositoryImpl @Inject constructor(
    private val roomDb: RoomDB,
    private val managementApi: ManagementApi,
): ManagementRepository {

    companion object {
        private const val EFFECT_DELAY = 2 * 1000L
    }

    override suspend fun createMong(name: String, sleepStart: String, sleepEnd: String) {

        val response = managementApi.createMong(createMongRequestDto = CreateMongRequestDto(
            name = name,
            sleepAt = LocalTime.parse(sleepStart),
            wakeupAt = LocalTime.parse(sleepEnd),
        ));

        if (!response.isSuccessful) {
            throw InvalidCreateMongException(name = name, sleepStart = sleepStart, sleepEnd = sleepEnd)
        }
    }

    override suspend fun deleteMong(mongId: Long) {

        val response = managementApi.deleteMong(mongId = mongId)

        if (!response.isSuccessful) {
            throw InvalidDeleteMongException(mongId = mongId)
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

        throw InvalidGetFeedItemsException(mongId = mongId)
    }

    override suspend fun feedMong(mongId: Long, foodTypeCode: String) {

        val response = managementApi.feedMong(mongId = mongId, feedMongRequestDto = FeedMongRequestDto(foodTypeCode = foodTypeCode))

        if (!response.isSuccessful) {
            throw InvalidFeedMongException(mongId = mongId, foodTypeCode = foodTypeCode)
        }
    }

    override suspend fun graduateMong(mongId: Long) {

        val response = managementApi.graduateMong(mongId = mongId)

        if (!response.isSuccessful) {
            throw InvalidGraduateMongException(mongId = mongId)
        }
    }

    override suspend fun graduateCheckMong(mongId: Long) {

        roomDb.mongDao().findByMongId(mongId = mongId)?.let { mongEntity ->

            mongEntity.graduateCheck()

            roomDb.mongDao().save(mongEntity)
        }
    }

    override suspend fun evolutionMong(mongId: Long) {

        val response = managementApi.evolutionMong(mongId = mongId)

        if (!response.isSuccessful) {
            throw InvalidEvolutionMongException(mongId = mongId)
        }
    }

    override suspend fun sleepingMong(mongId: Long) {

        val response = managementApi.sleepMong(mongId = mongId)

        if (!response.isSuccessful) {
            throw InvalidSleepMongException(mongId = mongId)
        }
    }

    override suspend fun strokeMong(mongId: Long) {

        val response = managementApi.strokeMong(mongId = mongId)

        if (!response.isSuccessful) {
            throw InvalidStrokeMongException(mongId = mongId)
        }
    }

    override suspend fun poopCleanMong(mongId: Long) {

        val response = managementApi.poopCleanMong(mongId = mongId)

        if (!response.isSuccessful) {
            throw InvalidPoopCleanMongException(mongId = mongId)
        }
    }

    override suspend fun setIsHappy(mongId: Long) {

        roomDb.mongDao().findByMongId(mongId = mongId)?.let { mongEntity ->

            CoroutineScope(Dispatchers.IO).launch {
                mongEntity.happy()
                roomDb.mongDao().save(mongEntity)

                delay(EFFECT_DELAY)

                mongEntity.resetFlag()
                roomDb.mongDao().save(mongEntity)
            }
        }
    }

    override suspend fun setIsEating(mongId: Long) {

        roomDb.mongDao().findByMongId(mongId = mongId)?.let { mongEntity ->

            CoroutineScope(Dispatchers.IO).launch {
                mongEntity.eat()
                roomDb.mongDao().save(mongEntity)

                delay(EFFECT_DELAY)

                mongEntity.resetFlag()
                roomDb.mongDao().save(mongEntity)
            }
        }
    }

    override suspend fun setIsPoopCleaning(mongId: Long) {

        roomDb.mongDao().findByMongId(mongId = mongId)?.let { mongEntity ->

            CoroutineScope(Dispatchers.IO).launch {
                mongEntity.poopClean()
                roomDb.mongDao().save(mongEntity)

                delay(EFFECT_DELAY)

                mongEntity.resetFlag()
                roomDb.mongDao().save(mongEntity)
            }
        }
    }
}
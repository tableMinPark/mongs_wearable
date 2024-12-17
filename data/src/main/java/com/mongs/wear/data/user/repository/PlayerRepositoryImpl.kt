package com.mongs.wear.data.user.repository

import androidx.lifecycle.LiveData
import com.mongs.wear.data.user.api.PlayerApi
import com.mongs.wear.data.user.datastore.PlayerDataStore
import com.mongs.wear.data.user.dto.request.ExchangeWalkingRequestDto
import com.mongs.wear.data.user.exception.InvalidBuySlotException
import com.mongs.wear.data.user.exception.InvalidExchangeWalkingException
import com.mongs.wear.data.user.exception.InvalidUpdatePlayerException
import com.mongs.wear.domain.repositroy.PlayerRepository
import javax.inject.Inject

class PlayerRepositoryImpl @Inject constructor(
    private val playerApi: PlayerApi,
    private val playerDataStore: PlayerDataStore,
): PlayerRepository {

    override suspend fun updatePlayer() {

        val response = playerApi.getPlayer()

        if (response.isSuccessful) {
            response.body()?.let { body ->
                playerDataStore.setStarPoint(starPoint = body.result.starPoint)
                playerDataStore.setSlotCount(maxSlot = body.result.slotCount)
                playerDataStore.setWalkingCount(walkingCount = body.result.walkingCount)
            }
        } else {
            throw InvalidUpdatePlayerException()
        }
    }

    override suspend fun buySlot() {

        val response = playerApi.buySlot()

        if (!response.isSuccessful) {
            throw InvalidBuySlotException()
        }
    }

    override suspend fun getStarPointLive(): LiveData<Int> = playerDataStore.getStarPointLive()

    override suspend fun getSlotCountLive(): LiveData<Int> = playerDataStore.getSlotCountLive()

    override suspend fun setWalkingCount(walkingCount: Int) {
        playerDataStore.setWalkingCount(walkingCount = walkingCount)
    }

    override suspend fun getWalkingCountLive(): LiveData<Int> = playerDataStore.getWalkingCountLive()

    override suspend fun exchangeWalkingCount(mongId: Long, walkingCount: Int) {

        val response = playerApi.exchangeWalking(
            exchangeWalkingRequestDto = ExchangeWalkingRequestDto(
                mongId = mongId,
                walkingCount = walkingCount
            )
        )

        if (!response.isSuccessful) {
            throw InvalidExchangeWalkingException()
        }
    }
}
package com.mongs.wear.data.user.repository

import androidx.lifecycle.LiveData
import com.mongs.wear.data.user.api.PlayerApi
import com.mongs.wear.data.user.datastore.PlayerDataStore
import com.mongs.wear.data.user.dto.request.ChargeStarPointRequestDto
import com.mongs.wear.data.user.dto.request.ExchangeStarPointRequestDto
import com.mongs.wear.data.user.dto.request.ExchangeWalkingRequestDto
import com.mongs.wear.data.user.exception.BuySlotException
import com.mongs.wear.data.user.exception.ChargeStarPointException
import com.mongs.wear.data.user.exception.ExchangeStarPointException
import com.mongs.wear.data.user.exception.ExchangeWalkingException
import com.mongs.wear.data.user.exception.UpdatePlayerException
import com.mongs.wear.domain.player.repository.PlayerRepository
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
                playerDataStore.setSlotCount(slotCount = body.result.slotCount)
                playerDataStore.setWalkingCount(walkingCount = body.result.walkingCount)
            }
        } else {
            throw UpdatePlayerException()
        }
    }

    override suspend fun buySlot() {

        val response = playerApi.buySlot()

        if (!response.isSuccessful) {
            throw BuySlotException()
        }
    }

    override suspend fun getStarPointLive(): LiveData<Int> = playerDataStore.getStarPointLive()

    override suspend fun getSlotCountLive(): LiveData<Int> = playerDataStore.getSlotCountLive()

    override suspend fun setWalkingCount(walkingCount: Int) {
        playerDataStore.setWalkingCount(walkingCount = walkingCount)
    }

    override suspend fun getWalkingCountLive(): LiveData<Int> = playerDataStore.getWalkingCountLive()

    override suspend fun chargeStarPoint(receipt: String, starPoint: Int) {

        // TODO: 구글 영수증 검증 로직 필요

        val response = playerApi.chargeStarPoint(
            chargeStarPointRequestDto = ChargeStarPointRequestDto(
                receipt = receipt,
                starPoint = starPoint
            )
        )

        if (!response.isSuccessful) {
            throw ChargeStarPointException(receipt = receipt)
        }
    }

    override suspend fun exchangeStarPoint(mongId: Long, starPoint: Int) {

        val response = playerApi.exchangeStarPoint(
            exchangeStarPointRequestDto = ExchangeStarPointRequestDto(
                mongId = mongId,
                starPoint = starPoint,
            )
        )

        if (!response.isSuccessful) {
            throw ExchangeStarPointException(mongId = mongId)
        }
    }

    override suspend fun chargeWalking(walkingCount: Int) {

        playerDataStore.setWalkingCount(walkingCount = walkingCount)
    }

    override suspend fun exchangeWalking(mongId: Long, walkingCount: Int) {

        val totalWalkingCount = playerDataStore.getWalkingCount()

        val response = playerApi.exchangeWalkingCount(
            exchangeWalkingRequestDto = ExchangeWalkingRequestDto(
                mongId = mongId,
                walkingCount = walkingCount,
                totalWalkingCount = totalWalkingCount
            )
        )

        if (!response.isSuccessful) {
            throw ExchangeWalkingException()
        }
    }
}
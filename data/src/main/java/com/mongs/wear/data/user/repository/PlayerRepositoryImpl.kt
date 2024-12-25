package com.mongs.wear.data.user.repository

import androidx.lifecycle.LiveData
import com.mongs.wear.data.user.api.PlayerApi
import com.mongs.wear.data.user.datastore.PlayerDataStore
import com.mongs.wear.data.user.dto.request.ChargeStarPointRequestDto
import com.mongs.wear.data.user.dto.request.ExchangeStarPointRequestDto
import com.mongs.wear.data.user.dto.request.ExchangeWalkingCountRequestDto
import com.mongs.wear.data.user.dto.request.SyncWalkingCountRequestDto
import com.mongs.wear.data.user.exception.BuySlotException
import com.mongs.wear.data.user.exception.ChargeStarPointException
import com.mongs.wear.data.user.exception.ExchangeStarPointException
import com.mongs.wear.data.user.exception.ExchangeWalkingException
import com.mongs.wear.data.user.exception.UpdatePlayerException
import com.mongs.wear.domain.player.repository.PlayerRepository
import java.time.LocalDateTime
import javax.inject.Inject

class PlayerRepositoryImpl @Inject constructor(
    private val playerApi: PlayerApi,
    private val playerDataStore: PlayerDataStore,
): PlayerRepository {

    /**
     * 플레이어 정보 업데이트
     */
    override suspend fun updatePlayer() {

        val response = playerApi.getPlayer()

        if (response.isSuccessful) {
            response.body()?.let { body ->
                playerDataStore.setStarPoint(starPoint = body.result.starPoint)
                playerDataStore.setSlotCount(slotCount = body.result.slotCount)
            }
        } else {
            throw UpdatePlayerException()
        }
    }

    /**
     * 슬롯 구매
     */
    override suspend fun buySlot() {

        val response = playerApi.buySlot()

        if (!response.isSuccessful) {
            throw BuySlotException()
        }
    }

    /**
     * 스타 포인트 조회
     */
    override suspend fun getStarPointLive(): LiveData<Int> = playerDataStore.getStarPointLive()

    /**
     * 슬롯 카운트 조회
     */
    override suspend fun getSlotCountLive(): LiveData<Int> = playerDataStore.getSlotCountLive()

    /**
     * 스타 포인트 충전
     */
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

    /**
     * 스타 포인트 환전
     */
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

    /**
     * 걸음 수 조회
     */
    override suspend fun getStepsLive(): LiveData<Int> = playerDataStore.getStepsLive()

    /**
     * 걸음 수 동기화
     */
    override suspend fun syncWalkingCount(deviceId: String, totalWalkingCount: Int, deviceBootedDt: LocalDateTime) {

        playerDataStore.setTotalWalkingCount(totalWalkingCount = totalWalkingCount)

        val response = playerApi.syncWalkingCount(
            SyncWalkingCountRequestDto(
                deviceId = deviceId,
                totalWalkingCount = totalWalkingCount,
                deviceBootedDt = deviceBootedDt,
            )
        )

        if (response.isSuccessful) {
            response.body()?.let { body ->
                playerDataStore.setWalkingCount(walkingCount = body.result.walkingCount)
                playerDataStore.setConsumeWalkingCount(consumeWalkingCount = body.result.consumeWalkingCount)
            }
        }
    }

    /**
     * 걸음 수 환전
     */
    override suspend fun exchangeWalking(mongId: Long, walkingCount: Int, deviceBootedDt: LocalDateTime) {

        val totalWalkingCount = playerDataStore.getSteps()

        val response = playerApi.exchangeWalkingCount(
            exchangeWalkingCountRequestDto = ExchangeWalkingCountRequestDto(
                mongId = mongId,
                walkingCount = walkingCount,
                totalWalkingCount = totalWalkingCount,
                deviceBootedDt = deviceBootedDt,
            )
        )

        if (response.isSuccessful) {

            response.body()?.let { body ->
                playerDataStore.setWalkingCount(walkingCount = body.result.walkingCount)
                playerDataStore.setConsumeWalkingCount(consumeWalkingCount = body.result.consumeWalkingCount)
            }
        } else {
            throw ExchangeWalkingException()
        }
    }
}
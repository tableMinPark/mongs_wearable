package com.mongs.wear.data.user.repository

import androidx.lifecycle.LiveData
import com.mongs.wear.data.global.utils.HttpUtil
import com.mongs.wear.data.user.api.PlayerApi
import com.mongs.wear.data.user.datastore.PlayerDataStore
import com.mongs.wear.data.user.dto.request.ExchangeStarPointRequestDto
import com.mongs.wear.data.user.dto.request.ExchangeWalkingCountRequestDto
import com.mongs.wear.data.user.dto.request.SyncWalkingCountRequestDto
import com.mongs.wear.data.user.exception.BuySlotException
import com.mongs.wear.data.user.exception.ExchangeStarPointException
import com.mongs.wear.data.user.exception.ExchangeWalkingException
import com.mongs.wear.data.user.exception.GetPlayerException
import com.mongs.wear.domain.player.repository.PlayerRepository
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerRepositoryImpl @Inject constructor(
    private val httpUtil: HttpUtil,
    private val playerApi: PlayerApi,
    private val playerDataStore: PlayerDataStore,
): PlayerRepository {

    override suspend fun updatePlayer() {

        val response = playerApi.getPlayer()

        if (response.isSuccessful) {
            response.body()?.let { body ->
                playerDataStore.setStarPoint(starPoint = body.result.starPoint)
            }
        } else {
            throw GetPlayerException(result = httpUtil.getErrorResult(response.errorBody()))
        }
    }

    /**
     * 스타 포인트 조회
     */
    override suspend fun getStarPointLive(): LiveData<Int> {
        return playerDataStore.getStarPointLive()
    }

    /**
     * 슬롯 카운트 조회
     */
    override suspend fun getSlotCount(): Int {

        val response = playerApi.getPlayer()

        if (response.isSuccessful) {
            response.body()?.let { body ->
                return body.result.slotCount
            } ?: run {
                return 1
            }

        } else {
            throw GetPlayerException(result = httpUtil.getErrorResult(response.errorBody()))
        }
    }

    /**
     * 슬롯 구매
     */
    override suspend fun buySlot() {

        val response = playerApi.buySlot()

        if (!response.isSuccessful) {
            throw BuySlotException(result = httpUtil.getErrorResult(response.errorBody()))
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
            throw ExchangeStarPointException(result = httpUtil.getErrorResult(response.errorBody()))
        }
    }

    /**
     * 걸음 수 환전
     */
    override suspend fun exchangeWalkingCount(mongId: Long, walkingCount: Int, deviceBootedDt: LocalDateTime) {

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
            throw ExchangeWalkingException(result = httpUtil.getErrorResult(response.errorBody()))
        }
    }

    /**
     * 걸음 수 서버 동기화
     */
    override suspend fun syncTotalWalkingCount(deviceId: String, totalWalkingCount: Int, deviceBootedDt: LocalDateTime) {

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
     * 걸음 수 조회
     */
    override suspend fun getStepsLive(): LiveData<Int> = playerDataStore.getStepsLive()

    /**
     * 걸음 수 로컬 동기화
     */
    override suspend fun setTotalWalkingCount(totalWalkingCount: Int) {
        playerDataStore.setTotalWalkingCount(totalWalkingCount = totalWalkingCount)
    }
}
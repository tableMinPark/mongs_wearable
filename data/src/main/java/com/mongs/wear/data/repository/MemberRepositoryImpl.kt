package com.mongs.wear.data.repository

import androidx.lifecycle.LiveData
import com.mongs.wear.data.api.client.MemberApi
import com.mongs.wear.data.dataStore.MemberDataStore
import com.mongs.wear.data.dto.member.req.ExchangePayPointWalkingReqDto
import com.mongs.wear.data.room.client.RoomDB
import com.mongs.wear.domain.error.RepositoryErrorCode
import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.repositroy.MemberRepository
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(
    private val memberDataStore: MemberDataStore,
    private val memberApi: MemberApi,
    private val roomDB: RoomDB,
): MemberRepository {
    override suspend fun setMember() {
        val res = memberApi.findMember()

        if (res.isSuccessful) {
            res.body()?.let { body ->
                try {
                    memberDataStore.setMaxSlot(maxSlot = body.maxSlot)
                    memberDataStore.setStarPoint(starPoint = body.starPoint)
                } catch (e: RuntimeException) {
                    throw RepositoryException(
                        errorCode = RepositoryErrorCode.SET_MEMBER_FAIL,
                        throwable = e,
                    )
                }
            }
        } else {
            throw RepositoryException(RepositoryErrorCode.SET_MEMBER_FAIL)
        }
    }
    override suspend fun buySlot(): Int {
        val res = memberApi.buySlot()

        if (res.isSuccessful) {
            res.body()?.let { body ->
                try {
                    memberDataStore.setMaxSlot(maxSlot = body.maxSlot)
                    memberDataStore.setStarPoint(starPoint = body.starPoint)
                    return body.maxSlot
                } catch (e: RuntimeException) {
                    throw RepositoryException(
                        errorCode = RepositoryErrorCode.BUY_SLOT_FAIL,
                        throwable = e,
                    )
                }
            }
        }

        throw RepositoryException(RepositoryErrorCode.BUY_SLOT_FAIL)
    }
    override suspend fun setRefreshToken(refreshToken: String) {
        try {
            memberDataStore.setRefreshToken(refreshToken = refreshToken)
        } catch (e: RuntimeException) {
            throw RepositoryException(
                errorCode = RepositoryErrorCode.SET_REFRESH_TOKEN_FAIL,
                throwable = e,
            )
        }
    }
    override suspend fun getRefreshToken(): String {
        try {
            return memberDataStore.getRefreshToken()
        } catch (e: RuntimeException) {
            throw RepositoryException(
                errorCode = RepositoryErrorCode.GET_REFRESH_TOKEN_FAIL,
                throwable = e,
            )
        }
    }
    override suspend fun setAccessToken(accessToken: String) {
        try {
            memberDataStore.setAccessToken(accessToken = accessToken)
        } catch (e: RuntimeException) {
            throw RepositoryException(
                errorCode = RepositoryErrorCode.SET_ACCESS_TOKEN_FAIL,
                throwable = e,
            )
        }
    }
    override suspend fun getAccessToken(): String {
        try {
            return memberDataStore.getAccessToken()
        } catch (e: RuntimeException) {
            throw RepositoryException(
                errorCode = RepositoryErrorCode.GET_ACCESS_TOKEN_FAIL,
                throwable = e,
            )
        }
    }
    override suspend fun setStarPoint(starPoint: Int) {
        try {
            memberDataStore.setStarPoint(starPoint = starPoint)
        } catch (e: RuntimeException) {
            throw RepositoryException(
                errorCode = RepositoryErrorCode.SET_STAR_POINT_FAIL,
                throwable = e,
            )
        }
    }
    override suspend fun getStarPointLive(): LiveData<Int> {
        try {
            return memberDataStore.getStarPointLive()
        } catch (e: RuntimeException) {
            throw RepositoryException(
                errorCode = RepositoryErrorCode.GET_STAR_POINT_FAIL,
                throwable = e,
            )
        }
    }
    override suspend fun setMaxSlot(maxSlot: Int) {
        try {
            memberDataStore.setMaxSlot(maxSlot = maxSlot)
        } catch (e: RuntimeException) {
            throw RepositoryException(
                errorCode = RepositoryErrorCode.SET_MAX_SLOT_FAIL,
                throwable = e,
            )
        }
    }
    override suspend fun getMaxSlotLive(): LiveData<Int> {
        val res = memberApi.findMember()

        if (res.isSuccessful) {
            res.body()?.let { body ->
                try {
                    memberDataStore.setMaxSlot(maxSlot = body.maxSlot)
                    return memberDataStore.getMaxSlotLive()
                } catch (e: RuntimeException) {
                    throw RepositoryException(
                        errorCode = RepositoryErrorCode.GET_MAX_SLOT_FAIL,
                        throwable = e,
                    )
                }
            }
        }

        throw RepositoryException(RepositoryErrorCode.GET_MAX_SLOT_FAIL)
    }
    override suspend fun setStartStepCount(stepCount: Int) {
        try {
            memberDataStore.setStartStepCount(startStepCount = stepCount)
        } catch (e: RuntimeException) {
            throw RepositoryException(
                errorCode = RepositoryErrorCode.SET_WALKING_COUNT_FAIL,
                throwable = e,
            )
        }
    }
    override suspend fun getStartStepCount(): Int {
        try {
            return memberDataStore.getStartStepCount()
        } catch (e: RuntimeException) {
            throw RepositoryException(
                errorCode = RepositoryErrorCode.GET_WALKING_COUNT_FAIL,
                throwable = e,
            )
        }
    }
    override suspend fun setEndStepCount(stepCount: Int) {
        try {
            memberDataStore.setEndStepCount(endStepCount = stepCount)
        } catch (e: RuntimeException) {
            throw RepositoryException(
                errorCode = RepositoryErrorCode.SET_WALKING_COUNT_FAIL,
                throwable = e,
            )
        }
    }
    override suspend fun getEndStepCount(): Int {
        try {
            return memberDataStore.getEndStepCount()
        } catch (e: RuntimeException) {
            throw RepositoryException(
                errorCode = RepositoryErrorCode.GET_WALKING_COUNT_FAIL,
                throwable = e,
            )
        }
    }
    override suspend fun setWalkingCount(walkingCount: Int) {
        try {
            memberDataStore.setWalkingCount(walkingCount = walkingCount)
        } catch (e: RuntimeException) {
            throw RepositoryException(
                errorCode = RepositoryErrorCode.SET_WALKING_COUNT_FAIL,
                throwable = e,
            )
        }
    }
    override suspend fun getWalkingCount(): Int {
        try {
            return memberDataStore.getWalkingCount()
        } catch (e: RuntimeException) {
            throw RepositoryException(
                errorCode = RepositoryErrorCode.GET_WALKING_COUNT_FAIL,
                throwable = e,
            )
        }
    }
    override suspend fun getWalkingCountLive(): LiveData<Int> {
        try {
            return memberDataStore.getWalkingCountLive()
        } catch (e: RuntimeException) {
            throw RepositoryException(
                errorCode = RepositoryErrorCode.GET_WALKING_COUNT_FAIL,
                throwable = e,
            )
        }
    }
    override suspend fun exchangePayPointWalking(mongId: Long, walkingCount: Int) {
        val res = memberApi.exchangePayPointWalking(exchangePayPointWalkingReqDto =
            ExchangePayPointWalkingReqDto(
                mongId = mongId,
                walkingCount = walkingCount
            )
        )

        if (res.isSuccessful) {
            res.body()?.let { body ->
                try {
                    roomDB.slotDao().updatePayPointByExchangeWalking(
                        mongId = body.mongId,
                        payPoint = body.payPoint
                    )
                } catch (e: RuntimeException) {
                    throw RepositoryException(
                        errorCode = RepositoryErrorCode.EXCHANGE_PAY_POINT_WALKING_FAIL,
                        throwable = e,
                    )
                }
            }
        } else {
            throw RepositoryException(RepositoryErrorCode.EXCHANGE_PAY_POINT_WALKING_FAIL)
        }
    }
}
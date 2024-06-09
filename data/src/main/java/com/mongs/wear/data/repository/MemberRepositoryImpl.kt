package com.mongs.wear.data.repository

import androidx.lifecycle.LiveData
import com.mongs.wear.data.api.client.MemberApi
import com.mongs.wear.data.dataStore.MemberDataStore
import com.mongs.wear.domain.error.RepositoryErrorCode
import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.repositroy.MemberRepository
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(
    private val memberApi: MemberApi,
    private val memberDataStore: MemberDataStore
): MemberRepository {

    override suspend fun setMember() {
        val res = memberApi.findMember()

        if (res.isSuccessful) {
            val body = res.body()!!
            try {
                memberDataStore.setMaxSlot(maxSlot = body.maxSlot)
                memberDataStore.setStarPoint(starPoint = body.starPoint)
            } catch (e: RuntimeException) {
                throw RepositoryException(RepositoryErrorCode.SET_MEMBER_FAIL)
            }
        } else {
            throw RepositoryException(RepositoryErrorCode.SET_MEMBER_FAIL)
        }
    }
    override suspend fun buySlot(): Int {
        val res = memberApi.buySlot()

        if (res.isSuccessful) {
            val body = res.body()!!
            try {
                memberDataStore.setMaxSlot(maxSlot = body.maxSlot)
                memberDataStore.setStarPoint(starPoint = body.starPoint)
                return body.maxSlot
            } catch (e: RuntimeException) {
                throw RepositoryException(RepositoryErrorCode.BUY_SLOT_FAIL)
            }
        } else {
            throw RepositoryException(RepositoryErrorCode.BUY_SLOT_FAIL)
        }
    }
    override suspend fun setRefreshToken(refreshToken: String) {
        try {
            memberDataStore.setRefreshToken(refreshToken = refreshToken)
        } catch (e: RuntimeException) {
            throw RepositoryException(RepositoryErrorCode.SET_REFRESH_TOKEN_FAIL)
        }
    }
    override suspend fun getRefreshToken(): String {
        try {
            return memberDataStore.getRefreshToken()
        } catch (e: RuntimeException) {
            throw RepositoryException(RepositoryErrorCode.GET_REFRESH_TOKEN_FAIL)
        }
    }
    override suspend fun setAccessToken(accessToken: String) {
        try {
            memberDataStore.setAccessToken(accessToken = accessToken)
        } catch (e: RuntimeException) {
            throw RepositoryException(RepositoryErrorCode.SET_ACCESS_TOKEN_FAIL)
        }
    }
    override suspend fun getAccessToken(): String {
        try {
            return memberDataStore.getAccessToken()
        } catch (e: RuntimeException) {
            throw RepositoryException(RepositoryErrorCode.GET_ACCESS_TOKEN_FAIL)
        }
    }
    override suspend fun setStarPoint(starPoint: Int) {
        try {
            memberDataStore.setStarPoint(starPoint = starPoint)
        } catch (e: RuntimeException) {
            throw RepositoryException(RepositoryErrorCode.SET_STAR_POINT_FAIL)
        }
    }
    override suspend fun getStarPointLive(): LiveData<Int> {
        try {
            return memberDataStore.getStarPointLive()
        } catch (e: RuntimeException) {
            throw RepositoryException(RepositoryErrorCode.GET_STAR_POINT_FAIL)
        }
    }
    override suspend fun setMaxSlot(maxSlot: Int) {
        try {
            memberDataStore.setMaxSlot(maxSlot = maxSlot)
        } catch (e: RuntimeException) {
            throw RepositoryException(RepositoryErrorCode.SET_MAX_SLOT_FAIL)
        }
    }
    override suspend fun getMaxSlotLive(): LiveData<Int> {
        val res = memberApi.findMember()

        if (res.isSuccessful) {
            val body = res.body()!!
            try {
                memberDataStore.setMaxSlot(maxSlot = body.maxSlot)
                return memberDataStore.getMaxSlotLive()
            } catch (e: RuntimeException) {
                throw RepositoryException(RepositoryErrorCode.GET_MAX_SLOT_FAIL)
            }
        } else {
            throw RepositoryException(RepositoryErrorCode.GET_MAX_SLOT_FAIL)
        }
    }
    override suspend fun setWalkingCount(walkingCount: Int) {
        try {
            memberDataStore.setWalkingCount(walkingCount = walkingCount)
        } catch (e: RuntimeException) {
            throw RepositoryException(RepositoryErrorCode.SET_WALKING_COUNT_FAIL)
        }
    }
    override suspend fun getWalkingCountLive(): LiveData<Int> {
        try {
            return memberDataStore.getWalkingCountLive()
        } catch (e: RuntimeException) {
            throw RepositoryException(RepositoryErrorCode.GET_WALKING_COUNT_FAIL)
        }
    }

    override suspend fun addWalkingCount(addWalkingCount: Int) {
        try {
            val walkingCount = memberDataStore.getWalkingCount()
            memberDataStore.setWalkingCount(walkingCount = walkingCount + addWalkingCount)
        } catch (e: RuntimeException) {
            throw RepositoryException(RepositoryErrorCode.SET_WALKING_COUNT_FAIL)
        }
    }
}
package com.mongs.wear.data.repository

import androidx.lifecycle.LiveData
import com.mongs.wear.data.api.client.MemberApi
import com.mongs.wear.data.dataStore.MemberDataStore
import com.mongs.wear.domain.error.RepositoryErrorCode
import com.mongs.wear.domain.exception.parent.ApiException
import com.mongs.wear.domain.repositroy.MemberRepository
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(
    private val memberApi: MemberApi,
    private val memberDataStore: MemberDataStore
): MemberRepository {
    override suspend fun setMember(accountId: Long) {
        val res = memberApi.findMember()

        if (res.isSuccessful) {
            res.body()?.let { body ->
                memberDataStore.setMaxSlot(maxSlot = body.maxSlot)
                memberDataStore.setStarPoint(starPoint = body.starPoint)
            }
        } else {
            throw ApiException(RepositoryErrorCode.FIND_MEMBER_FAIL)
        }
    }
    override suspend fun buySlot(): Int {
        val res = memberApi.buySlot()

        if (res.isSuccessful) {
            res.body()?.let { body ->
                memberDataStore.setMaxSlot(maxSlot = body.maxSlot)
                memberDataStore.setStarPoint(starPoint = body.starPoint)
            }
        }

        throw ApiException(RepositoryErrorCode.BUY_SLOT_FAIL)
    }
    override suspend fun setRefreshToken(refreshToken: String) {
        memberDataStore.setRefreshToken(refreshToken = refreshToken)
    }
    override suspend fun getRefreshToken(): String {
        return memberDataStore.getRefreshToken()
    }
    override suspend fun setAccessToken(accessToken: String) {
        memberDataStore.setAccessToken(accessToken = accessToken)
    }
    override suspend fun getAccessToken(): String {
        return memberDataStore.getAccessToken()
    }
    override suspend fun setMaxSlot(maxSlot: Int) {
        memberDataStore.setMaxSlot(maxSlot = maxSlot)
    }
    override suspend fun getMaxSlotLive(): LiveData<Int> {
        return memberDataStore.getStarPointLive()
    }

    override suspend fun setStarPoint(starPoint: Int) {
        memberDataStore.setStarPoint(starPoint = starPoint)
    }
    override suspend fun getStarPointLive(): LiveData<Int> {
        return memberDataStore.getStarPointLive()
    }
}
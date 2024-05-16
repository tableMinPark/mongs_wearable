package com.paymong.wear.data.repository_.common

import androidx.lifecycle.LiveData
import com.paymong.wear.data.api.client.MemberApi
import com.paymong.wear.data.dataStore.MemberDataStore
import com.paymong.wear.domain.error.CommonErrorCode
import com.paymong.wear.domain.exception.CommonException
import com.paymong.wear.domain.repository.common.MemberRepository
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(
    private val memberApi: MemberApi,
    private val memberDataStore: MemberDataStore
) : MemberRepository {
    override suspend fun initializeMember() {
        val response = memberApi.findMember()

        if (response.isSuccessful) {
            response.body()?.let { findMemberResDto ->
                val starPoint = findMemberResDto.starPoint
                val maxSlot = findMemberResDto.maxSlot
                memberDataStore.setStarPoint(starPoint = starPoint)
                memberDataStore.setMaxSlot(maxSlot = maxSlot)
            }
        } else {
            throw CommonException(CommonErrorCode.GET_MEMBER_FAIL)
        }
    }

    override suspend fun setStarPoint(starPoint: Int) {
        memberDataStore.setStarPoint(starPoint = starPoint)
    }

    override suspend fun getStarPoint(): LiveData<Int> {
        return memberDataStore.getStarPointLive()
    }

    override suspend fun setMaxSlot(maxSlot: Int) {
        memberDataStore.setMaxSlot(maxSlot = maxSlot)
    }

    override suspend fun getMaxSlot(): Int {
        return memberDataStore.getMaxSlot()
    }
}
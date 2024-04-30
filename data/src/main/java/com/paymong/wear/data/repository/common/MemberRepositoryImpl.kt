package com.paymong.wear.data.repository.common

import androidx.lifecycle.LiveData
import com.paymong.wear.data.api.member.MemberApi
import com.paymong.wear.data.dataStore.member.MemberDataStore
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
                memberDataStore.modifyStarPoint(starPoint = starPoint)
                memberDataStore.modifyMaxSlot(maxSlot = maxSlot)
            }
        } else {
            throw CommonException(CommonErrorCode.GET_MEMBER_FAIL)
        }
    }

    override suspend fun setStarPoint(starPoint: Int) {
        memberDataStore.modifyStarPoint(starPoint = starPoint)
    }

    override suspend fun getStarPoint(): LiveData<Int> {
        return memberDataStore.findStarPoint()
    }

    override suspend fun setMaxSlot(maxSlot: Int) {
        memberDataStore.modifyMaxSlot(maxSlot = maxSlot)
    }

    override suspend fun getMaxSlot(): Int {
        return memberDataStore.findMaxSlot()
    }
}
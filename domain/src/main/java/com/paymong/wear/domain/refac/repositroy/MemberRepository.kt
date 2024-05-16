package com.paymong.wear.domain.refac.repositroy

interface MemberRepository {
    suspend fun setMember(accountId: Long)
}
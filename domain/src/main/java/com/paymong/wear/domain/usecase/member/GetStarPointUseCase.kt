package com.paymong.wear.domain.usecase.member

import androidx.lifecycle.LiveData
import com.paymong.wear.domain.repositroy.MemberRepository
import javax.inject.Inject

class GetStarPointUseCase @Inject constructor(
    private val memberRepository: MemberRepository
) {
    suspend operator fun invoke(): LiveData<Int> {
        return memberRepository.getStarPointLive()
    }
}
package com.mongs.wear.domain.usecase.member

import androidx.lifecycle.LiveData
import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.repositroy.MemberRepository
import javax.inject.Inject

class GetStarPointUseCase @Inject constructor(
    private val memberRepository: MemberRepository
) {
    suspend operator fun invoke(): LiveData<Int> {
        try {
            return memberRepository.getStarPointLive()
        } catch (e: RepositoryException) {
            throw UseCaseException(e.errorCode)
        }
    }
}
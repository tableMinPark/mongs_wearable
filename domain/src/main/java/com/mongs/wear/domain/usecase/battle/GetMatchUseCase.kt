package com.mongs.wear.domain.usecase.battle

import androidx.lifecycle.LiveData
import com.mongs.wear.domain.code.FeedbackCode
import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.repositroy.BattleRepository
import com.mongs.wear.domain.vo.MatchVo
import javax.inject.Inject

class GetMatchUseCase @Inject constructor(
    private val battleRepository: BattleRepository,
) {
    suspend operator fun invoke(): LiveData<MatchVo> {
        return battleRepository.getMatchLive()
    }
}
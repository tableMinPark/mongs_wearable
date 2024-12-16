package com.mongs.wear.domain.usecase.battle

import androidx.lifecycle.LiveData
import com.mongs.wear.domain.repositroy.BattleRepository
import com.mongs.wear.domain.vo.MatchPlayerVo
import javax.inject.Inject

class GetOtherMatchPlayerUseCase @Inject constructor(
    private val battleRepository: BattleRepository,
) {
    suspend operator fun invoke(): LiveData<MatchPlayerVo> {
        return battleRepository.getOtherMatchPlayerLive()
    }
}
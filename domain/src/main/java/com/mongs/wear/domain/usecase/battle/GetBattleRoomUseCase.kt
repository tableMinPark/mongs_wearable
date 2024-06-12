package com.mongs.wear.domain.usecase.battle

import androidx.lifecycle.LiveData
import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.repositroy.BattleRepository
import com.mongs.wear.domain.repositroy.SlotRepository
import com.mongs.wear.domain.vo.BattleRoomVo
import javax.inject.Inject

class GetBattleRoomUseCase @Inject constructor(
    private val battleRepository: BattleRepository,
) {
    suspend operator fun invoke(): LiveData<BattleRoomVo> {
        try {
            return battleRepository.getBattleRoom()
        } catch (e: RepositoryException) {
            throw UseCaseException(e.errorCode)
        }
    }
}
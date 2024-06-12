package com.mongs.wear.domain.usecase.battle

import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.repositroy.BattleRepository
import com.mongs.wear.domain.repositroy.SlotRepository
import javax.inject.Inject

class MatchSearchUseCase @Inject constructor(
    private val slotRepository: SlotRepository,
    private val battleRepository: BattleRepository,
) {
    suspend operator fun invoke() {
        try {
            val slotModel = slotRepository.getNowSlot()
            battleRepository.matchSearch(mongId = slotModel.mongId)
        } catch (e: RepositoryException) {
            throw UseCaseException(e.errorCode)
        }
    }
}
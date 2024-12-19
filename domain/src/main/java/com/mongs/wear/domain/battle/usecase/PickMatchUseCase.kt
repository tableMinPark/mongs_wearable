package com.mongs.wear.domain.battle.usecase

import com.mongs.wear.core.enums.MatchRoundCode
import com.mongs.wear.domain.battle.repository.BattleRepository
import javax.inject.Inject

class PickMatchUseCase @Inject constructor(
    private val battleRepository: BattleRepository,
) {
    suspend operator fun invoke(roomId: Long, playerId: String, targetPlayerId: String, pickCode: MatchRoundCode) {

        when (pickCode) {

            MatchRoundCode.MATCH_PICK_ATTACK ->
                battleRepository.pickMatch(roomId = roomId, targetPlayerId = targetPlayerId, pickCode = pickCode)

            MatchRoundCode.MATCH_PICK_DEFENCE ->
                battleRepository.pickMatch(roomId = roomId, targetPlayerId = playerId, pickCode = pickCode)

            MatchRoundCode.MATCH_PICK_HEAL ->
                battleRepository.pickMatch(roomId = roomId, targetPlayerId = playerId, pickCode = pickCode)

            else -> {}
        }
    }
}
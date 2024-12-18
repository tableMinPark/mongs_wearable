package com.mongs.wear.domain.player.usecase

import com.mongs.wear.domain.player.repository.PlayerRepository
import javax.inject.Inject

class BuySlotUseCase @Inject constructor(
    private val playerRepository: PlayerRepository,
) {
    suspend operator fun invoke() {
        playerRepository.buySlot()
    }
}
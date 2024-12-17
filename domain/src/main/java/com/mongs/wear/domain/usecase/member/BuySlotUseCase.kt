package com.mongs.wear.domain.usecase.member

import com.mongs.wear.domain.repositroy.PlayerRepository
import javax.inject.Inject

class BuySlotUseCase @Inject constructor(
    private val playerRepository: PlayerRepository,
) {
    suspend operator fun invoke() {
        playerRepository.buySlot()
    }
}
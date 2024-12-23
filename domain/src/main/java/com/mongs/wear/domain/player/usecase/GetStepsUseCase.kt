package com.mongs.wear.domain.player.usecase

import androidx.lifecycle.LiveData
import com.mongs.wear.domain.player.repository.PlayerRepository
import javax.inject.Inject

class GetStepsUseCase @Inject constructor(
    private val playerRepository: PlayerRepository,
) {
    suspend operator fun invoke(): LiveData<Int> = playerRepository.getStepsLive()
}
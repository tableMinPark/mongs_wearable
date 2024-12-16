package com.mongs.wear.domain.usecase.member

import androidx.lifecycle.LiveData
import com.mongs.wear.domain.repositroy.PlayerRepository
import javax.inject.Inject

class GetMaxSlotUseCase @Inject constructor(
    private val playerRepository: PlayerRepository,
) {
    suspend operator fun invoke(): LiveData<Int> {
        return playerRepository.getMaxSlotLive()
    }
}
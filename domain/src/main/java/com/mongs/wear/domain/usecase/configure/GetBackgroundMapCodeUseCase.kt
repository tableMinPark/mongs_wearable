package com.mongs.wear.domain.usecase.configure

import androidx.lifecycle.LiveData
import com.mongs.wear.domain.repositroy.AppRepository
import javax.inject.Inject

class GetBackgroundMapCodeUseCase @Inject constructor(
    private val appRepository: AppRepository,
) {
    suspend operator fun invoke(): LiveData<String> = appRepository.getBgMapTypeCodeLive()
}
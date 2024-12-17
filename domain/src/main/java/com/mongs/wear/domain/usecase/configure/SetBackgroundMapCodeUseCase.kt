package com.mongs.wear.domain.usecase.configure

import com.mongs.wear.domain.repositroy.AppRepository
import javax.inject.Inject

class SetBackgroundMapCodeUseCase @Inject constructor(
    private val appRepository: AppRepository,
) {
    suspend operator fun invoke() {
        appRepository.setBgMapTypeCode(mapTypeCode = "MP000")
    }
    suspend operator fun invoke(code: String) {
        appRepository.setBgMapTypeCode(mapTypeCode = code)
    }
}
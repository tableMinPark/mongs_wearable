package com.mongs.wear.domain.usecase.collection

import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.repositroy.CodeRepository
import com.mongs.wear.domain.vo.MongVo
import javax.inject.Inject

class GetMongCodeUseCase @Inject constructor(
    private val codeRepository: CodeRepository
) {
    suspend operator fun invoke(code: String): MongVo {
        try {
            val mongCodeModel = codeRepository.getMongCode(code = code)
            return MongVo(code = mongCodeModel.code, name = mongCodeModel.name)
        } catch (e: RepositoryException) {
            throw UseCaseException(e.errorCode)
        }
    }
}
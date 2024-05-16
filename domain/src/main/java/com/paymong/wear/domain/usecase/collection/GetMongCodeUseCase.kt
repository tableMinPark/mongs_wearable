package com.paymong.wear.domain.usecase.collection

import com.paymong.wear.domain.repositroy.CodeRepository
import com.paymong.wear.domain.vo.MongVo
import javax.inject.Inject

class GetMongCodeUseCase @Inject constructor(
    private val codeRepository: CodeRepository
) {
    suspend operator fun invoke(code: String): MongVo {
        val mongCodeModel = codeRepository.getMongCode(code = code)
        return MongVo(code = mongCodeModel.code, name = mongCodeModel.name)
    }
}
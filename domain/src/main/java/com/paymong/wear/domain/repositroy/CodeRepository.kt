package com.paymong.wear.domain.repositroy

import com.paymong.wear.domain.model.FoodCodeModel
import com.paymong.wear.domain.model.MongCodeModel

interface CodeRepository {
    suspend fun setCodes(codeIntegrity: String, buildVersion: String)
    suspend fun getMongCode(code: String): MongCodeModel
    suspend fun getFoodCodes(): List<FoodCodeModel>
    suspend fun getSnackCodes(): List<FoodCodeModel>
}
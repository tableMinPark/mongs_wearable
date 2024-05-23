package com.paymong.wear.domain.repositroy

import com.paymong.wear.domain.model.FoodCodeModel
import com.paymong.wear.domain.model.MongCodeModel
import com.paymong.wear.domain.model.VersionModel

interface CodeRepository {
    suspend fun validationVersion(codeIntegrity: String, buildVersion: String): VersionModel
    suspend fun setCodes(codeIntegrity: String, buildVersion: String)
    suspend fun getMongCode(code: String): MongCodeModel
    suspend fun getFoodCodes(): List<FoodCodeModel>
    suspend fun getSnackCodes(): List<FoodCodeModel>
}
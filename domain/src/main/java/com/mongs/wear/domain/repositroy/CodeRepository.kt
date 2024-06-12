package com.mongs.wear.domain.repositroy

import com.mongs.wear.domain.model.FoodCodeModel
import com.mongs.wear.domain.model.MongCodeModel
import com.mongs.wear.domain.model.VersionModel

interface CodeRepository {
    suspend fun validationVersion(codeIntegrity: String, buildVersion: String): VersionModel
    suspend fun setCodes(codeIntegrity: String, buildVersion: String)
    suspend fun getMongCode(code: String): MongCodeModel
    suspend fun getFoodCodes(): List<FoodCodeModel>
    suspend fun getSnackCodes(): List<FoodCodeModel>
}
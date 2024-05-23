package com.paymong.wear.data.repository

import com.paymong.wear.data.api.client.CommonApi
import com.paymong.wear.data.dataStore.DeviceDataStore
import com.paymong.wear.data.dto.common.req.FindCodeReqDto
import com.paymong.wear.data.dto.common.req.FindVersionReqDto
import com.paymong.wear.data.room.client.RoomDB
import com.paymong.wear.data.room.entity.FoodCode
import com.paymong.wear.data.room.entity.MapCode
import com.paymong.wear.data.room.entity.MongCode
import com.paymong.wear.domain.error.RepositoryErrorCode
import com.paymong.wear.domain.exception.ApiException
import com.paymong.wear.domain.model.FoodCodeModel
import com.paymong.wear.domain.model.MongCodeModel
import com.paymong.wear.domain.model.VersionModel
import com.paymong.wear.domain.repositroy.CodeRepository
import javax.inject.Inject

class CodeRepositoryImpl @Inject constructor(
    private val deviceDataStore: DeviceDataStore,
    private val commonApi: CommonApi,
    private val roomDB: RoomDB,
): CodeRepository {

    override suspend fun validationVersion(codeIntegrity: String, buildVersion: String): VersionModel {
        val res = commonApi.findVersion(
            FindVersionReqDto(
                codeIntegrity = codeIntegrity,
                buildVersion = buildVersion,
            )
        )

        if (res.isSuccessful) {
            res.body()?.let { body ->
                return VersionModel(
                    newestBuildVersion = body.newestBuildVersion,
                    createdAt = body.createdAt,
                    updateApp = body.updateApp,
                    updateCode = body.updateCode,
                )
            }
        }

        throw ApiException(RepositoryErrorCode.VALIDATION_VERSION_FAIL)
    }

    override suspend fun setCodes(codeIntegrity: String, buildVersion: String) {
        val res = commonApi.findCode(
            FindCodeReqDto(
                buildVersion = buildVersion,
            )
        )

        if (res.isSuccessful) {
            res.body()?.let { body ->
                roomDB.mapCodeDao().deleteAll()
                body.mapCodeList.forEach { mapCode ->
                    roomDB.mapCodeDao().register(
                        MapCode(
                            code = mapCode.code,
                            name = mapCode.name,
                            buildVersion = mapCode.buildVersion,
                        )
                    )
                }

                roomDB.mongCodeDao().deleteAll()
                body.mongCodeList.forEach { mongCode ->
                    roomDB.mongCodeDao().register(
                        MongCode(
                            code = mongCode.code,
                            name = mongCode.name,
                            buildVersion = mongCode.buildVersion,
                        )
                    )
                }

                roomDB.foodCodeDao().deleteAll()
                body.foodCodeList.forEach { foodCode ->
                    roomDB.foodCodeDao().register(
                        FoodCode(
                            code = foodCode.code,
                            name = foodCode.name,
                            groupCode = foodCode.groupCode,
                            price = foodCode.price,
                            addWeight = foodCode.addWeight,
                            addStrength = foodCode.addStrength,
                            addSatiety = foodCode.addSatiety,
                            addHealthy = foodCode.addHealthy,
                            addSleep = foodCode.addSleep,
                            buildVersion = foodCode.buildVersion,
                        )
                    )
                }

                deviceDataStore.setCodeIntegrity(codeIntegrity = body.codeIntegrity)
            }
        } else {
            throw ApiException(RepositoryErrorCode.SYNC_CODE_FAIL)
        }
    }
    override suspend fun getMongCode(code: String): MongCodeModel {
        return roomDB.mongCodeDao().findByCode(code = code).let { mongCode ->
            MongCodeModel(
                code = mongCode.code,
                name = mongCode.name,
            )
        }
    }
    override suspend fun getFoodCodes(): List<FoodCodeModel> {
        return roomDB.foodCodeDao().findByGroupCode("FD").map { foodCode ->
            FoodCodeModel(
                code = foodCode.code,
                name = foodCode.name,
                price = foodCode.price,
                addWeight = foodCode.addWeight,
                addStrength = foodCode.addStrength,
                addSatiety = foodCode.addSatiety,
                addHealthy = foodCode.addHealthy,
                addSleep = foodCode.addSleep,
            )
        }
    }
    override suspend fun getSnackCodes(): List<FoodCodeModel> {
        return roomDB.foodCodeDao().findByGroupCode("SN").map { foodCode ->
            FoodCodeModel(
                code = foodCode.code,
                name = foodCode.name,
                price = foodCode.price,
                addWeight = foodCode.addWeight,
                addStrength = foodCode.addStrength,
                addSatiety = foodCode.addSatiety,
                addHealthy = foodCode.addHealthy,
                addSleep = foodCode.addSleep,
            )
        }
    }
}
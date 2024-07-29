package com.mongs.wear.data.repository

import com.mongs.wear.data.api.client.CommonApi
import com.mongs.wear.data.dataStore.DeviceDataStore
import com.mongs.wear.data.dto.common.req.FindCodeReqDto
import com.mongs.wear.data.dto.common.req.FindVersionReqDto
import com.mongs.wear.data.room.client.RoomDB
import com.mongs.wear.data.room.entity.FoodCode
import com.mongs.wear.data.room.entity.MapCode
import com.mongs.wear.data.room.entity.MongCode
import com.mongs.wear.domain.error.RepositoryErrorCode
import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.model.FoodCodeModel
import com.mongs.wear.domain.model.MongCodeModel
import com.mongs.wear.domain.model.VersionModel
import com.mongs.wear.domain.repositroy.CodeRepository
import javax.inject.Inject

class CodeRepositoryImpl @Inject constructor(
    private val deviceDataStore: DeviceDataStore,
    private val commonApi: CommonApi,
    private val roomDB: RoomDB,
): CodeRepository {

    companion object {
        const val FOOD_GROUP_CODE = "FD"
        const val SNACK_GROUP_CODE = "SN"
    }

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

        throw RepositoryException(RepositoryErrorCode.VALIDATION_VERSION_FAIL)
    }
    override suspend fun setCodes(codeIntegrity: String, buildVersion: String) {
        val res = commonApi.findCode(
            FindCodeReqDto(
                buildVersion = buildVersion,
            )
        )

        if (res.isSuccessful) {
            res.body()?.let { body ->
                try {
                    roomDB.mapCodeDao().deleteAll()
                    body.mapCodeList.forEach { mapCode ->
                        roomDB.mapCodeDao().insert(
                            MapCode(
                                code = mapCode.code,
                                name = mapCode.name,
                                buildVersion = mapCode.buildVersion,
                            )
                        )
                    }

                    roomDB.mongCodeDao().deleteAll()
                    body.mongCodeList.forEach { mongCode ->
                        roomDB.mongCodeDao().insert(
                            MongCode(
                                code = mongCode.code,
                                name = mongCode.name,
                                buildVersion = mongCode.buildVersion,
                            )
                        )
                    }

                    roomDB.foodCodeDao().deleteAll()
                    body.foodCodeList.forEach { foodCode ->
                        roomDB.foodCodeDao().insert(
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

                } catch (e: RuntimeException) {
                    throw RepositoryException(
                        errorCode = RepositoryErrorCode.SET_CODES_FAIL,
                        throwable = e,
                    )
                }
            }
        } else {
            throw RepositoryException(RepositoryErrorCode.SET_CODES_FAIL)
        }
    }
    override suspend fun getMongCode(code: String): MongCodeModel {
        try {
            return roomDB.mongCodeDao().selectByCode(code = code).let { mongCode ->
                MongCodeModel(
                    code = mongCode.code,
                    name = mongCode.name,
                )
            }
        } catch (e: RuntimeException) {
            throw RepositoryException(
                errorCode = RepositoryErrorCode.GET_MONG_CODE_FAIL,
                throwable = e,
            )
        }
    }
    override suspend fun getFoodCodes(): List<FoodCodeModel> {
        try {
            return roomDB.foodCodeDao().selectByGroupCode(FOOD_GROUP_CODE).map { foodCode ->
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
        } catch (e: RuntimeException) {
            throw RepositoryException(
                errorCode = RepositoryErrorCode.GET_FOOD_CODES_FAIL,
                throwable = e,
            )
        }
    }
    override suspend fun getSnackCodes(): List<FoodCodeModel> {
        try {
            return roomDB.foodCodeDao().selectByGroupCode(SNACK_GROUP_CODE).map { foodCode ->
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
        } catch (e: RuntimeException) {
            throw RepositoryException(
                errorCode = RepositoryErrorCode.GET_SNACK_CODES_FAIL,
                throwable = e,
            )
        }
    }
}
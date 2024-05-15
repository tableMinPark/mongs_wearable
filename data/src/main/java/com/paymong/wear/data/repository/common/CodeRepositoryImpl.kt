package com.paymong.wear.data.repository.common

import android.util.Log
import com.paymong.wear.data.api.client.CommonApi
import com.paymong.wear.data.dataStore.DeviceDataStore
import com.paymong.wear.data.room.client.RoomDB
import com.paymong.wear.data.room.entity.FoodCode
import com.paymong.wear.data.room.entity.MapCode
import com.paymong.wear.data.room.entity.MongCode
import com.paymong.wear.domain.error.CommonErrorCode
import com.paymong.wear.domain.exception.CommonException
import com.paymong.wear.domain.repository.common.CodeRepository
import com.paymong.wear.domain.repository.common.vo.FeedbackCodeVo
import com.paymong.wear.domain.repository.common.vo.FoodCodeVo
import com.paymong.wear.domain.repository.common.vo.MapCodeVo
import com.paymong.wear.domain.repository.common.vo.MongCodeVo
import javax.inject.Inject


class CodeRepositoryImpl @Inject constructor(
    private val deviceDataStore: DeviceDataStore,
    private val commonApi: CommonApi,
    private val roomDB: RoomDB
) : CodeRepository {
    override suspend fun initializeCode() {
        val codeIntegrity = deviceDataStore.findCodeIntegrity()
        val buildVersion = deviceDataStore.findBuildVersion()
        val response = commonApi.findNewestVersion(
            buildVersion = buildVersion,
            codeIntegrity = codeIntegrity,
        )

        if (response.isSuccessful) {
            response.body()?.let { findVersionResDto ->
                val mustUpdateApp = findVersionResDto.mustUpdateApp
                val mustUpdateCode = findVersionResDto.mustUpdateCode

                if (mustUpdateApp) {
                    throw CommonException(CommonErrorCode.MUST_UPDATE_APP)
                }
                if (mustUpdateCode) {
                    Log.d("initCode", "update code")
                    this.syncCode()
                } else {
                    Log.d("initCode", "not update code")
                }
            }
        } else {
            throw CommonException(CommonErrorCode.VERSION_CHECK_FAIL)
        }
    }

    private suspend fun syncCode() {
        val response = commonApi.findCode()

        if (response.isSuccessful) {
            response.body()?.let { findCodeResDto ->

                deviceDataStore.modifyCodeIntegrity(codeIntegrity = findCodeResDto.codeIntegrity)

                val mapCodeDao = roomDB.mapCodeDao()
                val mongCodeDao = roomDB.mongCodeDao()
                val foodCodeDao = roomDB.foodCodeDao()

                mapCodeDao.deleteAll()
                findCodeResDto.mapCodeList.forEach { findMapCodeResDto ->
                    val code = findMapCodeResDto.code
                    val name = findMapCodeResDto.name
                    val newestVersion = findMapCodeResDto.buildVersion
                    mapCodeDao.register(MapCode(code = code, name = name, buildVersion = newestVersion))
                }

                mongCodeDao.deleteAll()
                findCodeResDto.mongCodeList.forEach { findMongCodeResDto ->
                    val code = findMongCodeResDto.code
                    val name = findMongCodeResDto.name
                    val newestVersion = findMongCodeResDto.buildVersion
                    mongCodeDao.register(MongCode(code = code, name = name, buildVersion = newestVersion))
                }

                foodCodeDao.deleteAll()
                findCodeResDto.foodCodeList.forEach { findFoodCodeResDto ->
                    val code = findFoodCodeResDto.code
                    val groupCode = findFoodCodeResDto.groupCode
                    val name = findFoodCodeResDto.name
                    val price = findFoodCodeResDto.price
                    val addWeight = findFoodCodeResDto.addWeight
                    val addStrength = findFoodCodeResDto.addStrength
                    val addSatiety = findFoodCodeResDto.addSatiety
                    val addHealthy = findFoodCodeResDto.addHealthy
                    val addSleep = findFoodCodeResDto.addSleep
                    val newestVersion = findFoodCodeResDto.buildVersion
                    foodCodeDao.register(
                        FoodCode(
                        code = code,
                        groupCode = groupCode,
                        name = name,
                        price = price,
                        addWeight = addWeight,
                        addStrength = addStrength,
                        addSatiety = addSatiety,
                        addHealthy = addHealthy,
                        addSleep = addSleep,
                        buildVersion = newestVersion)
                    )
                }
            }
        } else {
            throw CommonException(CommonErrorCode.INIT_CODE_FAIL)
        }
    }

    override suspend fun getFoodCodeByGroupCode(groupCode: String): List<FoodCodeVo> {
        val foodCodeVoList = ArrayList<FoodCodeVo>()
        val foodCodeList = roomDB.foodCodeDao().findByGroupCode(groupCode = groupCode)

        foodCodeList.stream().forEach { foodCode ->
            val code = foodCode.code
            val name = foodCode.name
            val price = foodCode.price
            val addWeight = foodCode.addWeight
            val addStrength = foodCode.addStrength
            val addSatiety = foodCode.addSatiety
            val addHealthy = foodCode.addHealthy
            val addSleep = foodCode.addSleep

            foodCodeVoList.add(FoodCodeVo(
                code = code,
                name = name,
                price = price,
                addWeight = addWeight,
                addStrength = addStrength,
                addSatiety = addSatiety,
                addHealthy = addHealthy,
                addSleep = addSleep,
            ))
        }

        return foodCodeVoList
    }

    override suspend fun getFeedbackGroupCode(): List<String> {
        return roomDB.feedbackCodeDao().findGroupCode()
    }

    override suspend fun getFeedbackCodeByGroupCode(groupCode: String): List<FeedbackCodeVo> {
        val feedbackCodeVoList = ArrayList<FeedbackCodeVo>()
        val feedbackCodeList = roomDB.feedbackCodeDao().findByGroupCode(groupCode = groupCode)

        feedbackCodeList.stream().forEach { feedbackCode ->
            val code = feedbackCode.code
            val message = feedbackCode.message
            feedbackCodeVoList.add(FeedbackCodeVo(code = code, message = message))
        }

        return feedbackCodeVoList
    }

    override suspend fun getMapCode(code: String): MapCodeVo {
        val mapCode = roomDB.mapCodeDao().findByCode(code)
        return MapCodeVo(code = mapCode.code, name = mapCode.name)
    }

    override suspend fun getMongCode(code: String): MongCodeVo {
        val mongCode = roomDB.mongCodeDao().findByCode(code)
        return MongCodeVo(code = mongCode.code, name = mongCode.name)
    }
}
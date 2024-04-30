package com.paymong.wear.domain.repository.common

import com.paymong.wear.domain.repository.common.vo.FeedbackCodeVo
import com.paymong.wear.domain.repository.common.vo.FoodCodeVo
import com.paymong.wear.domain.repository.common.vo.MapCodeVo
import com.paymong.wear.domain.repository.common.vo.MongCodeVo

interface CodeRepository {
    suspend fun initializeCode()
    suspend fun getFoodCodeByGroupCode(groupCode: String): List<FoodCodeVo>
    suspend fun getFeedbackGroupCode(): List<String>
    suspend fun getFeedbackCodeByGroupCode(groupCode: String): List<FeedbackCodeVo>
    suspend fun getMapCode(code: String): MapCodeVo
    suspend fun getMongCode(code: String): MongCodeVo
}
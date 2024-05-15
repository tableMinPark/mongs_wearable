package com.paymong.wear.data.dto.common.res

import com.paymong.wear.data.vo.FindFoodCodeVo
import com.paymong.wear.data.vo.FindMapCodeVo
import com.paymong.wear.data.vo.FindMongCodeVo

data class FindCodeResDto(
    val codeIntegrity: String,
    val mapCodeList: List<FindMapCodeVo>,
    val mongCodeList: List<FindMongCodeVo>,
    val foodCodeList: List<FindFoodCodeVo>,
)
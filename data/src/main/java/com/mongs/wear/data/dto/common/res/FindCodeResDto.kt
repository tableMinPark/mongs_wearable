package com.mongs.wear.data.dto.common.res

import com.mongs.wear.data.vo.FindFoodCodeVo
import com.mongs.wear.data.vo.FindMapCodeVo
import com.mongs.wear.data.vo.FindMongCodeVo

data class FindCodeResDto(
    val codeIntegrity: String,
    val mapCodeList: List<FindMapCodeVo>,
    val mongCodeList: List<FindMongCodeVo>,
    val foodCodeList: List<FindFoodCodeVo>,
)
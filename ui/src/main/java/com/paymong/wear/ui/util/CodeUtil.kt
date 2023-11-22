package com.paymong.wear.ui.util

import com.paymong.wear.ui.code.MapCode
import com.paymong.wear.ui.code.MongCode
import com.paymong.wear.ui.code.StateCode

class CodeUtil {
    companion object {
        fun getMapCode(mapCode: String): MapCode {
            return MapCode.valueOf(mapCode)
        }

        fun getMongCode(mongCode: String): MongCode {
            return MongCode.valueOf(mongCode)
        }

        fun getStateCode(stateCode: String): StateCode {
            return StateCode.valueOf(stateCode)
        }
    }
}
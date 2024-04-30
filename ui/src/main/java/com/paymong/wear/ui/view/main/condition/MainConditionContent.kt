package com.paymong.wear.ui.view.main.condition

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import com.paymong.wear.domain.repository.slot.vo.SlotVo
import com.paymong.wear.ui.R
import com.paymong.wear.ui.global.theme.PaymongBlue
import com.paymong.wear.ui.global.theme.PaymongGreen
import com.paymong.wear.ui.global.theme.PaymongPink
import com.paymong.wear.ui.global.theme.PaymongPurple
import com.paymong.wear.ui.global.theme.PaymongYellow
import com.paymong.wear.ui.view.main.condition.component.Condition
import com.paymong.wear.ui.view.main.condition.component.Exp

@Composable
fun MainConditionContent(
    isPageChange: State<Boolean>,
    slotVo: State<SlotVo>,
) {
    Box(
        modifier = Modifier
            .zIndex(1f),
        contentAlignment = Alignment.Center
    ) {
        Exp(
            isPageChange = isPageChange.value,
            progress = slotVo.value.exp.toFloat(),
            indicatorColor = PaymongPurple
        )
    }

    Box(
        modifier = Modifier
            .zIndex(2f),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Condition(
                    icon = R.drawable.health,
                    progress = slotVo.value.healthy.toFloat(),
                    indicatorColor = PaymongPink
                )
                Condition(
                    icon = R.drawable.satiety,
                    progress = slotVo.value.satiety.toFloat(),
                    indicatorColor = PaymongYellow
                )
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Condition(
                    icon = R.drawable.strength,
                    progress = slotVo.value.strength.toFloat(),
                    indicatorColor = PaymongGreen
                )
                Condition(
                    icon = R.drawable.sleep,
                    progress = slotVo.value.sleep.toFloat(),
                    indicatorColor = PaymongBlue
                )
            }
        }
    }
}

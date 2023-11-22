package com.paymong.wear.ui.view.condition

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.CircularProgressIndicator
import com.paymong.wear.domain.viewModel.condition.ConditionViewModel
import com.paymong.wear.ui.R
import com.paymong.wear.ui.theme.PaymongBlue
import com.paymong.wear.ui.theme.PaymongGreen
import com.paymong.wear.ui.theme.PaymongRed
import com.paymong.wear.ui.theme.PaymongYellow

const val imageSize = 25
const val circularSize = 60
const val strokeWidth = 4
const val circularPadding = 5

@Composable
fun ConditionView(
    conditionViewModel: ConditionViewModel = hiltViewModel()
) {
    Box (
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                ConditionItem(
                    resourceId = R.drawable.health,
                    progress = conditionViewModel.health,
                    indicatorColor = PaymongRed
                )
                ConditionItem(
                    resourceId = R.drawable.satiety,
                    progress = conditionViewModel.satiety,
                    indicatorColor = PaymongYellow
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                ConditionItem(
                    resourceId = R.drawable.strength,
                    progress = conditionViewModel.strength,
                    indicatorColor = PaymongGreen
                )
                ConditionItem(
                    resourceId = R.drawable.sleep,
                    progress = conditionViewModel.sleep,
                    indicatorColor = PaymongBlue
                )
            }
        }
    }
}

@Composable
fun ConditionItem(
    resourceId: Int,
    progress: Float,
    indicatorColor: Color
) {
    Box(
        modifier = Modifier.padding(circularPadding.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(resourceId),
            contentDescription = null,
            modifier = Modifier.size(imageSize.dp)
        )
        CircularProgressIndicator(
            modifier = Modifier.size(circularSize.dp),
            startAngle = 271f,
            endAngle = 270f,
            progress = progress,
            strokeWidth = strokeWidth.dp,
            indicatorColor = indicatorColor,
        )
    }
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun ConditionViewPreView() {
    ConditionView()
}
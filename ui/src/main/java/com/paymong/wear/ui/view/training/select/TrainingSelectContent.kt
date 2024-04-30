package com.paymong.wear.ui.view.training.select

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.PositionIndicator
import com.paymong.wear.ui.R
import com.paymong.wear.ui.view.training.select.component.Training

@Composable
fun TrainingSelectContent(
    trainingBasketball: () -> Unit
) {
    /** 리스트 **/
    val listState = rememberScalingLazyListState(initialCenterItemIndex = 0)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(1f),
    ) {
        PositionIndicator(scalingLazyListState = listState)
        ScalingLazyColumn(
            contentPadding = PaddingValues(vertical = 60.dp, horizontal = 6.dp),
            modifier = Modifier.fillMaxSize(),
            state = listState,
            autoCentering = null,
        ) {
            item {
                Training(
                    icon = R.drawable.activity,
                    border = R.drawable.interaction_bnt_green,
                    fontColor = Color.White,
                    backgroundColor = Color.Gray,
                    label = "농구 훈련",
                    onClick = trainingBasketball,
                )
            }
        }
    }

}
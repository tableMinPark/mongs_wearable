package com.mongs.wear.presentation.view.trainingMenu

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.PositionIndicator
import com.mongs.wear.presentation.R
import com.mongs.wear.presentation.global.component.background.TrainingNestedBackground
import com.mongs.wear.presentation.global.component.common.IconChip
import com.mongs.wear.presentation.global.resource.NavItem

@Composable
fun TrainingMenuView(
    navController: NavController,
    context: Context = LocalContext.current,
) {
    Box {
        TrainingNestedBackground()
        TrainingMenuContent(
            jumping = {
                navController.navigate(route = NavItem.TrainingJumping.route)
            },
            basketball = {
                Toast.makeText(context, "업데이트 예정", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.zIndex(1f),
        )
    }
}

@Composable
private fun TrainingMenuContent(
    jumping: () -> Unit,
    basketball: () -> Unit,
    modifier: Modifier = Modifier.zIndex(0f),
) {
    val listState = rememberScalingLazyListState(initialCenterItemIndex = 0)

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        PositionIndicator(scalingLazyListState = listState)
        ScalingLazyColumn(
            contentPadding = PaddingValues(vertical = 60.dp, horizontal = 6.dp),
            modifier = Modifier.fillMaxSize(),
            state = listState,
            autoCentering = null,
        ) {
            item {
                IconChip(
                    icon = R.drawable.runner,
                    border = R.drawable.interaction_bnt_green,
                    fontColor = Color.White,
                    backgroundColor = Color.Black,
                    label = "Runner",
                    onClick = jumping,
                )
            }

            item {
                IconChip(
                    icon = R.drawable.basketball,
                    border = R.drawable.interaction_bnt_orange,
                    fontColor = Color.White,
                    backgroundColor = Color.Black,
                    label = "Basketball",
                    onClick = basketball,
                )
            }
        }
    }
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_SMALL_ROUND)
@Composable
private fun FeedMenuViewPreview() {
    Box {
        TrainingNestedBackground()
        TrainingMenuContent(
            jumping = {},
            basketball = {},
            modifier = Modifier.zIndex(1f),
        )
    }
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_LARGE_ROUND)
@Composable
private fun LargeFeedMenuViewPreview() {
    Box {
        TrainingNestedBackground()
        TrainingMenuContent(
            jumping = {},
            basketball = {},
            modifier = Modifier.zIndex(1f),
        )
    }
}


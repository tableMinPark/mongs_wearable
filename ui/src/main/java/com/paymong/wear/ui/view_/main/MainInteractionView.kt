package com.paymong.wear.ui.view_.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.Text
import com.paymong.wear.ui.R

@Composable
fun MainInteractionView() {

    Box {
        MainInteractionViewContent(
            training = {},
            battle = {},
            feed = {},
            sleep = {},
            poop = {},
            slotSelect = {},
        )
    }
}

@Composable
fun MainInteractionViewContent(
    training: () -> Unit,
    battle: () -> Unit,
    feed: () -> Unit,
    sleep: () -> Unit,
    poop: () -> Unit,
    slotSelect: () -> Unit,
) {
    val listState = rememberScalingLazyListState(initialCenterItemIndex = 2)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(1f),
    ) {
        ScalingLazyColumn(
            contentPadding = PaddingValues(vertical = 60.dp, horizontal = 6.dp),
            modifier = Modifier.fillMaxSize(),
            state = listState,
            autoCentering = null,
        ) {
            item {
                Interaction(
                    icon = R.drawable.activity,
                    border = R.drawable.interaction_bnt_green,
                    fontColor = Color.White,
                    backgroundColor = Color.Gray,
                    label = "훈련",
                    onClick = training
                )
            }
            item {
                Interaction(
                    icon = R.drawable.battle,
                    border = R.drawable.interaction_bnt_pink,
                    fontColor = Color.White,
                    backgroundColor = Color.Gray,
                    label = "배틀",
                    onClick = battle
                )
            }
            item {
                Interaction(
                    icon = R.drawable.feed,
                    border = R.drawable.interaction_bnt_orange,
                    fontColor = Color.White,
                    backgroundColor = Color.Gray,
                    label = "밥/간식 주기",
                    onClick = feed
                )
            }
            item {
                Interaction(
                    icon = R.drawable.poop,
                    border = R.drawable.interaction_bnt_purple,
                    fontColor = Color.White,
                    backgroundColor = Color.Gray,
                    label = "똥 치우기",
                    onClick = poop
                )
            }
            item {
                Interaction(
                    icon = R.drawable.sleep,
                    border = R.drawable.interaction_bnt_blue,
                    fontColor = Color.White,
                    backgroundColor = Color.Gray,
                    label = "재우기",
                    onClick = sleep
                )
            }
            item {
                Interaction(
                    icon = R.drawable.ch100,
                    border = R.drawable.interaction_bnt_orange,
                    fontColor = Color.White,
                    backgroundColor = Color.Gray,
                    label = "몽 슬롯",
                    onClick = slotSelect
                )
            }
        }
    }
}

@Composable
fun Interaction(
    icon: Int,
    border: Int,
    fontColor: Color,
    backgroundColor: Color,
    label: String,
    onClick: () -> Unit
) {
    Chip(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        icon = {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Image(
                    alpha = 0.8f,
                    painter = painterResource(R.drawable.interaction_bnt),
                    contentDescription = null,
                    modifier = Modifier.zIndex(0f)
                )
                Image(
                    painter = painterResource(icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .zIndex(1f)
                )
                Image(
                    painter = painterResource(border),
                    contentDescription = null,
                    modifier = Modifier.zIndex(1f)
                )
            }
        },
        colors = ChipDefaults.chipColors(
            contentColor = fontColor,
            backgroundColor = backgroundColor.copy(alpha = 0.3f)
        ),
        label = {
            Text(
                text = label,
                maxLines = 1,
                fontSize = 13.sp,
                modifier = Modifier.padding(start = 14.dp)
            )
        }
    )
}
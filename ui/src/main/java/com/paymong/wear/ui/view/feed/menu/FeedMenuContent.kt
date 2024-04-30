package com.paymong.wear.ui.view.feed.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.wear.compose.material.Text

@Composable
fun FeedMenuContent(
    food: () -> Unit,
    snack: () -> Unit,
) {
    Box(
        modifier = Modifier
            .zIndex(1f)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight()
        ) {
            // 밥 버튼
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.weight(0.49f)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = food
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "밥", fontSize = 18.sp)
                }
            }
            // Spacer
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.weight(0.02f)
            ) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .height(2.dp)
                )
            }
            // 간식 버튼
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.weight(0.49f)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = snack
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "간식", fontSize = 18.sp)
                }
            }
        }
    }
}
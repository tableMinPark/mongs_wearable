package com.paymong.wear.ui.view.feed

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
import androidx.navigation.NavController
import androidx.wear.compose.material.Text
import com.paymong.wear.ui.code.NavItem
import com.paymong.wear.ui.view.common.background.FeedBackGround

@Composable
fun FeedView(
    navController: NavController
) {
    /** Background **/
    FeedBackGround()

    /** Content **/
    FeedContent(
        navFood = { navController.navigate("${NavItem.FeedSelect.route}/FD") },
        navSnack = { navController.navigate("${NavItem.FeedSelect.route}/SN") }
    )
}

@Composable
fun FeedContent(
    navFood: () -> Unit,
    navSnack: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {
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
                        onClick = navFood
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "밥", fontSize = 16.sp)
            }
        }

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
                        onClick = navSnack
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "간식", fontSize = 16.sp)
            }
        }
    }
}
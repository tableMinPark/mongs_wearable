package com.paymong.wear.ui.view.collection.menu

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
import androidx.navigation.NavController
import androidx.wear.compose.material.Text
import com.paymong.wear.ui.global.resource.NavItem
import com.paymong.wear.ui.global.component.CollectionMenuBackground

@Composable
fun CollectionMenuView(
    navController: NavController,
) {
    Box {
        /** Background **/
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(0f)
        ) {
            CollectionMenuBackground()
        }

        CollectionMenuContent(
            mong = { navController.navigate("${NavItem.CollectionSelect.route}/MONG") },
            map = { navController.navigate("${NavItem.CollectionSelect.route}/MAP") }
        )
    }
}
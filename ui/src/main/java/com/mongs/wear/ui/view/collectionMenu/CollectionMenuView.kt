package com.mongs.wear.ui.view.collectionMenu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.navigation.NavController

@Composable
fun CollectionMenuView(
    navController: NavController,
) {
    Box {
        CollectionMenuContent(modifier = Modifier.zIndex(1f))
    }
}

@Composable
private fun CollectionMenuContent(
    modifier: Modifier = Modifier.zIndex(0f),
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxWidth()
    ) {

    }
}
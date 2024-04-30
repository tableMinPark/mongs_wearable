package com.paymong.wear.ui.view.collection.mapDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.paymong.wear.ui.view.collection.component.ListButton
import com.paymong.wear.ui.view.collection.component.MapSelectButton

@Composable
fun CollectionMapContent(
    navCollectionSelect: () -> Unit,
    mapSelect: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .fillMaxSize()
            .zIndex(1f)
            .padding(bottom = 30.dp)
    ) {
        Row (
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp)
        ) {
            ListButton(
                onClick = navCollectionSelect
            )
            MapSelectButton(
                onClick = mapSelect
            )
        }
    }
}

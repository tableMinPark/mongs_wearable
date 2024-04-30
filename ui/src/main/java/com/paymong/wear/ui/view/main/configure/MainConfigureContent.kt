package com.paymong.wear.ui.view.main.configure

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.paymong.wear.ui.R
import com.paymong.wear.ui.view.main.configure.component.Configure


@Composable
fun MainConfigureViewContent(
    signOut: () -> Unit,
    mapSearch: () -> Unit,
    charge: () -> Unit,
    feedback: () -> Unit,
) {
    Box(
        modifier = Modifier
            .zIndex(1f),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight()
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Configure(
                    icon = R.drawable.charge_startpoint,
                    border = R.drawable.interaction_bnt_darkpurple,
                    onClick = charge,
                    modifier = Modifier.padding(5.dp)
                )
                Configure(
                    icon = R.drawable.map_search,
                    border = R.drawable.interaction_bnt_darkpurple,
                    onClick = mapSearch,
                    modifier = Modifier.padding(5.dp)
                )
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Configure(
                    icon = R.drawable.feedback,
                    border = R.drawable.interaction_bnt_darkpurple,
                    onClick = feedback,
                    modifier = Modifier.padding(5.dp)
                )
                Configure(
                    icon = R.drawable.logout,
                    border = R.drawable.interaction_bnt_darkpurple,
                    onClick = signOut,
                    modifier = Modifier.padding(5.dp)
                )
            }
        }
    }
}
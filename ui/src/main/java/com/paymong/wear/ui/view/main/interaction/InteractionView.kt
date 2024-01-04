package com.paymong.wear.ui.view.main.interaction

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.paymong.wear.ui.R
import com.paymong.wear.ui.code.NavItem
import com.paymong.wear.ui.code.StateCode

@Composable
fun InteractionView(
    navController: NavController,
    stateCode: State<String>
) {
    val context = LocalContext.current

    /** Data **/
    val state = StateCode.valueOf(stateCode.value)

    /** Content **/
    InteractionContent(
        navMapCollection = { navController.navigate(NavItem.MapCollection.route) },
        navMongCollection = { navController.navigate(NavItem.MongCollection.route) },
        navTraining = {
            if (state == StateCode.CD444) {
                Toast.makeText(context, "몽을 생성해 주세요!", Toast.LENGTH_SHORT).show()
            } else {
                navController.navigate(NavItem.Training.route)
            }
        },
        navWalk = {
            if (state == StateCode.CD444) {
                Toast.makeText(context, "몽을 생성해 주세요!", Toast.LENGTH_SHORT).show()
            } else {
                navController.navigate(NavItem.Walk.route)
            }
        }
    )
}

@Composable
fun InteractionContent(
    navMapCollection: () -> Unit,
    navMongCollection: () -> Unit,
    navTraining: () -> Unit,
    navWalk: () -> Unit
) {
    Box (
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                InteractionButton(
                    icon = R.drawable.battle,
                    border = R.drawable.interaction_bnt_pink,
                    onClick = navMapCollection
                )
                InteractionButton(
                    icon = R.drawable.sleep,
                    border = R.drawable.interaction_bnt_blue,
                    onClick = navMongCollection
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                InteractionButton(
                    icon = R.drawable.activity,
                    border = R.drawable.interaction_bnt_green,
                    onClick = navTraining
                )
                InteractionButton(
                    icon = R.drawable.activity,
                    border = R.drawable.interaction_bnt_green,
                    onClick = navWalk
                )
            }
        }
    }
}
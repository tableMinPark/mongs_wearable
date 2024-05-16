package com.paymong.wear.ui.view.main.interaction

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.paymong.wear.domain.vo.SlotVo
import com.paymong.wear.ui.viewModel.main.MainInteractionViewModel
import com.paymong.wear.ui.global.resource.NavItem

@Composable
fun MainInteractionView(
    slotVo: State<SlotVo>,
    scrollPage: (Int) -> Unit,
    navController: NavController,
    mainInteractionViewModel: MainInteractionViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {
    val scrollToMainSlot = { scrollPage(1) }

    Box {
        MainInteractionContent(
            slotVo = slotVo,
            training = { navController.navigate(NavItem.TrainingNested.route) },
            battle = {
                     /*navController.navigate(NavItem.BattleNested.route)*/
                Toast.makeText(
                    context,
                    "준비중입니다",
                    Toast.LENGTH_SHORT
                ).show()
            },
            feed = { navController.navigate(NavItem.FeedNested.route) },
            sleep = {
                mainInteractionViewModel.sleep()
                scrollToMainSlot()
            },
            poop = {
                mainInteractionViewModel.poop()
                scrollToMainSlot()
            },
            collection = { navController.navigate(NavItem.CollectionNested.route) },
            slotSelect = { navController.navigate(NavItem.SlotSelect.route) },
        )
    }
}

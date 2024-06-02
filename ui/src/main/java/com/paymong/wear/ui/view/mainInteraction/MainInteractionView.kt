package com.paymong.wear.ui.view.mainInteraction

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.paymong.wear.domain.vo.SlotVo
import com.paymong.wear.ui.viewModel.mainInteraction.MainInteractionViewModel

@Composable
fun MainInteractionView(
    navController: NavController,
    scrollPage: (Int) -> Unit,
    slotVo: State<SlotVo>,
    mainInteractionViewModel: MainInteractionViewModel = hiltViewModel(),
) {
    Box {
        MainInteractionContent(modifier = Modifier.zIndex(1f))
    }
}

@Composable
private fun MainInteractionContent(
    slotVo: SlotVo = SlotVo(),
    modifier: Modifier = Modifier.zIndex(0f),
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxWidth()
    ) {

    }
}
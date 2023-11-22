package com.paymong.wear.ui.view.figure

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.paymong.wear.domain.viewModel.figure.FigureViewModel
import com.paymong.wear.ui.code.MongCode
import com.paymong.wear.ui.code.StateCode
import com.paymong.wear.ui.util.CodeUtil

@Composable
fun FigureView(
    figureViewModel: FigureViewModel = hiltViewModel()
) {
    FigureViewMainLayer(figureViewModel)
    FigureViewSubLayer(figureViewModel)
    FigureViewAnimationLayer(figureViewModel)
}

@Composable
fun FigureViewMainLayer(
    figureViewModel: FigureViewModel
) {
    val mongCode = CodeUtil.getMongCode(figureViewModel.mongCode)
    val stateCode = CodeUtil.getStateCode(figureViewModel.stateCode)

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .zIndex(2f)
            .fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight()
        ) {
            // 몽 없음
            if (mongCode == MongCode.CH444) {
                CharacterEmpty()
            }
            // 몽 있음
            else {
                when(stateCode) {
                    StateCode.CD005 -> CharacterDead()
                    StateCode.CD006 -> CharacterGraduation()
                    StateCode.CD007 -> CharacterEvolutionReady()
                    StateCode.CD010 -> CharacterEvolution()
                    else -> Character(stateCode, mongCode, figureViewModel::stroke)
                }
            }
        }
    }
}

@Composable
fun FigureViewSubLayer(
    figureViewModel: FigureViewModel
) {
    Row(
        modifier = Modifier
            .zIndex(3f)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxHeight()
        ) {
            Poop(figureViewModel.poopCount)
        }
    }
}

@Composable
fun FigureViewAnimationLayer(
    figureViewModel: FigureViewModel
) {
    val stateCode = CodeUtil.getStateCode(figureViewModel.stateCode)

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .zIndex(1f)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxHeight()
        ) {
            // 쓰다 듬기
            if (stateCode == StateCode.CD009) {
                Heart()
            }
        }
    }
}


@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun FigureViewPreView() {
    FigureView()
}
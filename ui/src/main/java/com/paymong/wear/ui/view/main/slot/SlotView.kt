package com.paymong.wear.ui.view.main.slot

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.paymong.wear.domain.viewModel.main.SlotViewModel
import com.paymong.wear.ui.code.MongCode
import com.paymong.wear.ui.code.StateCode
import com.paymong.wear.ui.view.common.character.Character

@Composable
fun SlotView(
    showActionContent: () -> Unit,
    slotViewModel: SlotViewModel = hiltViewModel()
) {
    /** Data **/
    val mongCode = slotViewModel.mongCode.observeAsState("")
    val stateCode = slotViewModel.stateCode.observeAsState("")
    val poopCount = slotViewModel.poopCount.observeAsState(0)

    /** Content **/
    SlotContent(
        mongCode = mongCode,
        stateCode = stateCode,
        poopCount = poopCount,
        evolutionStart = slotViewModel::evolutionStart,
        evolutionEnd = slotViewModel::evolutionEnd,
        graduation = slotViewModel::graduation,
        generateMong = slotViewModel::generateMong,
        showSlotActionView = showActionContent
    )
}

@Composable
fun SlotContent(
    mongCode: State<String>,
    stateCode: State<String>,
    poopCount: State<Int>,
    evolutionStart: () -> Unit,
    evolutionEnd: () -> Unit,
    graduation: () -> Unit,
    generateMong: () -> Unit,
    showSlotActionView: () -> Unit
) {
    val mong = MongCode.valueOf(mongCode.value)
    val state = StateCode.valueOf(stateCode.value)
    val poop = poopCount.value

    Box(
        modifier = Modifier.fillMaxSize().padding(bottom = 10.dp).zIndex(0f)
    ) {
        val zIndex = if (state in arrayListOf(
                StateCode.CD006,
                StateCode.CD010
            )
        ) 1f else -1f

        /** Main Layer **/
        Box(
            modifier = Modifier.zIndex(0f)
        ) {
            when (state) {
                StateCode.CD005 -> Dead()
                StateCode.CD007 -> EvolutionReady(evolutionStart = evolutionStart)
                StateCode.CD010 -> Character(mong = mong)
                StateCode.CD444 -> Empty(onClick = generateMong)
                else -> Character(
                    state = state,
                    mong = mong,
                    showSlotActionView = showSlotActionView
                )
            }
        }

        /** Sub Layer **/
        Box(
            modifier = Modifier
                .zIndex(2f)
        ) {
            if (state !in arrayOf(
                    StateCode.CD005,
                    StateCode.CD006,
                    StateCode.CD007,
                    StateCode.CD010,
                    StateCode.CD444
                )
            ) {
                Poop(poop)
            }
        }

        /** Animation Layer **/
        Box(
            modifier = Modifier
                .zIndex(zIndex)
        ) {
            when (state) {
                StateCode.CD009 -> Heart()
                StateCode.CD006 -> GraduationEffect(graduation = graduation)
                StateCode.CD010 -> EvolutionEffect(evolutionEnd = evolutionEnd)
                else -> {}
            }
        }
    }
}

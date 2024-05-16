package com.paymong.wear.ui.view.slotSelect

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.paymong.wear.domain.processCode.SlotSelectProcessCode
import com.paymong.wear.ui.viewModel.slotSelect.SlotSelectViewModel
import com.paymong.wear.ui.global.component.LoadingBar
import com.paymong.wear.ui.global.resource.NavItem
import com.paymong.wear.ui.global.component.SlotSelectBackground
import kotlinx.coroutines.delay

@Composable
fun SlotSelectView(
    navController: NavController,
    scrollPage: (Int) -> Unit,
    slotSelectViewModel: SlotSelectViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {
    /** Flag **/
    val processCode = slotSelectViewModel.processCode.observeAsState(SlotSelectProcessCode.STAND_BY)
    /** UI Flag **/
    val isLoadingBarShow = remember{ mutableStateOf(true) }
    val isSlotListLoad = remember{ mutableStateOf(false) }
    /** Observer **/
    val slotBuyPrice = remember { mutableIntStateOf(0) }
    val maxSlot = slotSelectViewModel.maxSlot.observeAsState(DefaultValue.MAX_SLOT)
    val slotList = slotSelectViewModel.slotList.observeAsState(ArrayList())
    val starPoint = slotSelectViewModel.starPoint.observeAsState(DefaultValue.STAR_POINT)

    when (processCode.value) {
        SlotSelectProcessCode.ADD_SLOT -> {
            isLoadingBarShow.value = true
        }
        SlotSelectProcessCode.REMOVE_SLOT -> {
            isLoadingBarShow.value = true
        }
        SlotSelectProcessCode.SET_SLOT -> {
            isLoadingBarShow.value = true
        }
        SlotSelectProcessCode.CHANGE_SLOT_END -> {
            LaunchedEffect(Unit) {
                delay(DefaultValue.LOAD_DELAY)
                isLoadingBarShow.value = false
                slotSelectViewModel.resetProcessCode()
            }
        }
        SlotSelectProcessCode.LOAD_SLOT_LIST_END -> {
            LaunchedEffect(Unit) {
                delay(DefaultValue.LOAD_DELAY)
                isLoadingBarShow.value = false
                isSlotListLoad.value = true
                slotSelectViewModel.resetProcessCode()
            }
        }
        SlotSelectProcessCode.BUY_SLOT_FAIL -> {
            isLoadingBarShow.value = false
            Toast.makeText(
                context,
                SlotSelectProcessCode.BUY_SLOT_FAIL.message,
                Toast.LENGTH_SHORT
            ).show()
            slotSelectViewModel.resetProcessCode()
        }
        SlotSelectProcessCode.ADD_SLOT_FAIL -> {
            isLoadingBarShow.value = false
            Toast.makeText(
                context,
                SlotSelectProcessCode.ADD_SLOT_FAIL.message,
                Toast.LENGTH_SHORT
            ).show()
            slotSelectViewModel.resetProcessCode()
        }
        SlotSelectProcessCode.REMOVE_SLOT_FAIL -> {
            isLoadingBarShow.value = false
            Toast.makeText(
                context,
                SlotSelectProcessCode.REMOVE_SLOT_FAIL.message,
                Toast.LENGTH_SHORT
            ).show()
            slotSelectViewModel.resetProcessCode()
        }
        SlotSelectProcessCode.SET_SLOT_FAIL -> {
            isLoadingBarShow.value = false
            Toast.makeText(
                context,
                SlotSelectProcessCode.SET_SLOT_FAIL.message,
                Toast.LENGTH_SHORT
            ).show()
            slotSelectViewModel.resetProcessCode()
        }
        SlotSelectProcessCode.NAV_MAIN -> {
            navController.popBackStack(route = NavItem.SlotSelect.route, inclusive = true)
        }
        else -> {}
    }

    Box {
        /** Background **/
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(0f)
        ) {
            SlotSelectBackground()
        }

        SlotSelectContent(
            slotBuyPrice = slotBuyPrice,
            starPoint = starPoint,
            maxSlot = maxSlot,
            slotList = slotList,
            isLoadingBarShow = isLoadingBarShow,
            isSlotListLoad = isSlotListLoad,
            addSlot = { name ->
                slotSelectViewModel.addSlot(name = name)
            },
            buySlot = { slotSelectViewModel.buySlot() },
            selectSlot = { slotId ->
                scrollPage(1)
                slotSelectViewModel.selectSlot(slotId)
            },
            removeSlot = { slotId ->
                slotSelectViewModel.removeSlot(slotId)
            },
            graduationSlot = { slotId ->
                slotSelectViewModel.graduation(slotId)
            },
        )

        /** 로딩 스피너 표출 (슬롯 리스트 로딩 시) **/
        if (isLoadingBarShow.value) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(color = Color.Black.copy(alpha = 0.4f))
                    .fillMaxSize()
                    .zIndex(3f)
            ) {
                LoadingBar(size = 50)
            }
        }
    }

    LaunchedEffect(Unit) {
        isLoadingBarShow.value = true
        isSlotListLoad.value = false
        slotSelectViewModel.loadSlotList()
    }
}


package com.paymong.wear.ui.view.collection.select

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.paymong.wear.domain.processCode.CollectionSelectProcessCode
import com.paymong.wear.ui.viewModel.collection.CollectionSelectViewModel
import com.paymong.wear.ui.global.resource.NavItem
import com.paymong.wear.ui.global.component.CollectionSelectBackground
import kotlinx.coroutines.delay

@Composable
fun CollectionSelectView(
    navController: NavController,
    code: String,
    collectionSelectViewModel: CollectionSelectViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {
    /** Process Code **/
    val processCode = collectionSelectViewModel.processCode.observeAsState(CollectionSelectProcessCode.STAND_BY)
    /** UI Flag **/
    val isLoadingBarShow = remember{ mutableStateOf(true) }
    val isCollectionItemListLoad = remember{ mutableStateOf(false) }
    /** Observe **/
    val collectionItemList = collectionSelectViewModel.collectionItemList.observeAsState(ArrayList())

    when (processCode.value) {
        CollectionSelectProcessCode.LOAD_COLLECTION_ITEM_LIST_FAIL -> {
            Toast.makeText(
                context,
                CollectionSelectProcessCode.LOAD_COLLECTION_ITEM_LIST_FAIL.message,
                Toast.LENGTH_SHORT
            ).show()
            navController.popBackStack()
        }
        CollectionSelectProcessCode.LOAD_COLLECTION_ITEM_LIST_END -> {
            LaunchedEffect(Unit) {
                delay(DefaultValue.LOAD_DELAY)
                isLoadingBarShow.value = false
                isCollectionItemListLoad.value = true
                collectionSelectViewModel.resetProcessCode()
            }
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
            CollectionSelectBackground()
        }

        CollectionSelectContent(
            code = code,
            collectionItemList = collectionItemList,
            isLoadingBarShow = isLoadingBarShow,
            isCollectionItemListLoad = isCollectionItemListLoad,
            showMongDetail = { code ->
                navController.navigate("${NavItem.CollectionMong.route}/$code")
            },
            showMapDetail = { code ->
                navController.navigate("${NavItem.CollectionMap.route}/$code")
            },
            cantShowDetail = {
                Toast.makeText(
                    context,
                    CollectionSelectProcessCode.CANT_SHOW_DETAIL.message,
                    Toast.LENGTH_SHORT
                ).show()
            },
        )
    }

    LaunchedEffect(Unit) {
        isLoadingBarShow.value = true
        isCollectionItemListLoad.value = false
        collectionSelectViewModel.loadCollectionItemList(code)
    }
}

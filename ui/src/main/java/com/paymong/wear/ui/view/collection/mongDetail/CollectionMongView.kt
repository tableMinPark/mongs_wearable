package com.paymong.wear.ui.view.collection.mongDetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.paymong.wear.domain.DefaultValue
import com.paymong.wear.domain.viewModel.collection.CollectionMongViewModel
import com.paymong.wear.ui.global.component.CollectionMongBackground

@Composable
fun CollectionMongView(
    navController: NavController,
    code: String,
    collectionMongViewModel: CollectionMongViewModel = hiltViewModel()
) {
    val mongCodeVo = collectionMongViewModel.mongCodeVo.observeAsState(DefaultValue.MONG_CODE_VO)

    Box {
        /** Background **/
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(0f)
        ) {
            CollectionMongBackground()
        }

        CollectionMongContent(
            mongCodeVo = mongCodeVo,
            navCollectionSelect = { navController.popBackStack() }
        )
    }

    LaunchedEffect(Unit) {
        collectionMongViewModel.getMongCode(mongCode = code)
    }
}

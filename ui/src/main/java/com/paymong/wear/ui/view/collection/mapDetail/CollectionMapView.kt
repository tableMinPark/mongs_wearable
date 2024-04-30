package com.paymong.wear.ui.view.collection.mapDetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.paymong.wear.domain.viewModel.collection.CollectionMapViewModel
import com.paymong.wear.ui.global.resource.NavItem
import com.paymong.wear.ui.global.component.CollectionMapBackground

@Composable
fun CollectionMapView(
    navController: NavController,
    scrollPage: (Int) -> Unit,
    code: String,
    collectionMapViewModel: CollectionMapViewModel = hiltViewModel()
) {
    Box {
        /** Background **/
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(0f)
        ) {
            CollectionMapBackground(code)
        }

        CollectionMapContent(
            navCollectionSelect = { navController.popBackStack() },
            mapSelect = {
                collectionMapViewModel.mapSelect(mapCode = code)
                scrollPage(1)
                navController.popBackStack(route = NavItem.CollectionNested.route, inclusive = true)
            }
        )
    }
}
package com.paymong.wear.ui.view.initLanding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.paymong.wear.ui.global.component.LoadingBar
import com.paymong.wear.ui.global.component.Logo
import com.paymong.wear.ui.view.initLanding.component.SignIn

@Composable
fun InitLandingContent(
    logoSize: State<Int>,
    googleSignInWidth: State<Int>,
    isLoadingBarShow: State<Boolean>,
    isSignInShow: State<Boolean>,
    googleSignIn: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(1f),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                /** Logo **/
                Logo(size = logoSize.value)
            }
            Row(
                modifier = Modifier.padding(top = 15.dp)
            ) {
                if (isSignInShow.value) {
                    /** Google Login Button **/
                    SignIn(width = googleSignInWidth.value, onClick = googleSignIn)
                } else if (isLoadingBarShow.value) {
                    /** Loading Bar **/
                    LoadingBar(size = 30)
                }
            }
        }
    }
}
package com.paymong.wear.ui.global.component.common

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun Alert(
    text: String,
    context: Context = LocalContext.current,
) {
    Toast.makeText(LocalContext.current, text, Toast.LENGTH_SHORT).show()
}
package com.paymong.wear

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.paymong.wear.ui.viewModel.mainActivity.MainActivityViewModel
import com.paymong.wear.ui.global.theme.PaymongTheme
import dagger.hilt.android.AndroidEntryPoint
import android.Manifest
import android.util.Log
import com.paymong.wear.ui.view.main.MainView
import org.eclipse.paho.android.service.BuildConfig


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED) {
            val permissions = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            ActivityCompat.requestPermissions(this, permissions, 100)
        }

        mainActivityViewModel.initDeviceInfo(applicationContext.packageManager.getPackageInfo(packageName, 0).versionName)
        mainActivityViewModel.initMqtt()

        setContent {
            PaymongTheme {
                MainView()
            }
        }
    }

    override fun onResume() {
        mainActivityViewModel.reconnectMqtt()
        super.onResume()
    }

    override fun onPause() {
        mainActivityViewModel.disconnectMqtt()
        super.onPause()
    }

    override fun onDestroy() {
        mainActivityViewModel.disconnectMqtt()
        mainActivityViewModel.initMqtt()
        super.onDestroy()
    }
}
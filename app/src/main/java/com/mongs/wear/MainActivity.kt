package com.mongs.wear

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.hardware.SensorManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.mongs.wear.ui.global.theme.MongsTheme
import com.mongs.wear.ui.view.main.MainView
import com.mongs.wear.ui.viewModel.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (
//            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
//            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED
        ) {
            val permissions = arrayOf(
//                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACTIVITY_RECOGNITION,
            )
            ActivityCompat.requestPermissions(this, permissions, 100)
        }

        mainViewModel.init(
            buildVersion = BuildConfig.VERSION_NAME,
            isNetworkAvailable = isNetworkAvailable(this.applicationContext)
        )

        setContent {
            MongsTheme {
                MainView(
                    closeApp = { this.finish() }
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.connectSensor(sensorManager = application.getSystemService(Context.SENSOR_SERVICE) as SensorManager)
        mainViewModel.reconnectMqttEvent()
    }

    override fun onPause() {
        super.onPause()
        mainViewModel.disconnectSensor()
        mainViewModel.pauseConnectMqttEvent()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainViewModel.disconnectMqttEvent()
        mainViewModel.disconnectMqttBattle()
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
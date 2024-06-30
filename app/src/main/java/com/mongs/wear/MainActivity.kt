package com.mongs.wear

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.mongs.wear.ui.viewModel.main.MainViewModel
import com.mongs.wear.ui.global.theme.MongsTheme
import dagger.hilt.android.AndroidEntryPoint
import android.Manifest
import android.content.Context
import android.hardware.SensorManager
import android.util.Log
import com.mongs.wear.ui.view.main.MainView


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

        mainViewModel.init(buildVersion = BuildConfig.VERSION_NAME)

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
}
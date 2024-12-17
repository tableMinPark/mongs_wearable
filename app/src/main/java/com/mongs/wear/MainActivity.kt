package com.mongs.wear

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.SensorManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.mongs.wear.presentation.assets.MongsTheme
import com.mongs.wear.presentation.layout.MainView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainActivityViewModel: MainActivityViewModel by viewModels()

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

        mainActivityViewModel.init(network = this.isNetworkAvailable(this))

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
        mainActivityViewModel.connectSensor(sensorManager = application.getSystemService(Context.SENSOR_SERVICE) as SensorManager)
        mainActivityViewModel.resumeConnectMqtt()
    }

    override fun onPause() {
        super.onPause()
        mainActivityViewModel.disconnectSensor()
        mainActivityViewModel.pauseConnectMqtt()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainActivityViewModel.disconnectMqtt()
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
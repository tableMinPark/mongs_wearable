package com.mongs.wear.activity

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.mongs.wear.presentation.assets.MongsTheme
import com.mongs.wear.presentation.layout.MainView
import com.mongs.wear.viewModel.MainActivityViewModel
import com.mongs.wear.worker.StepsWorker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    @Inject lateinit var workerManager: WorkManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * 권한 확인
         */
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

        workerManager.enqueueUniquePeriodicWork(
            StepsWorker.WORKER_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            PeriodicWorkRequestBuilder<StepsWorker>(15, TimeUnit.MINUTES)
                .setInitialDelay(15, TimeUnit.SECONDS)
                .build()
        )

        /**
         * 네트워크 가용 여부 확인
         */
        mainActivityViewModel.setNetworkAvailable(network = this.isNetworkAvailable(this))

        /**
         * UI 로딩
         */
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
        mainActivityViewModel.connectSensor()
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
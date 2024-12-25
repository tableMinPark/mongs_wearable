package com.mongs.wear.activity

import android.Manifest
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.mongs.wear.R
import com.mongs.wear.module.NotificationModule
import com.mongs.wear.presentation.assets.MongsTheme
import com.mongs.wear.presentation.layout.MainView
import com.mongs.wear.viewModel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    @Inject lateinit var notificationManager: NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        /**
         * 권한 확인
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                val permissions = arrayOf(Manifest.permission.POST_NOTIFICATIONS)
                ActivityCompat.requestPermissions(this, permissions, 100)
            }
        } else {
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
        }

        val notificationBuilder = NotificationCompat.Builder(this, NotificationModule.CHANNEL_ID)
            .setContentTitle("FCM Message")
            .setContentText("FCM Body")
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setAutoCancel(true)

        notificationManager.notify(0, notificationBuilder.build())

        /**
         * UI 로딩
         */
        setContent {
            MongsTheme {
                MainView()
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
}
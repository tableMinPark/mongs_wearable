package com.mongs.wear.viewModel

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.SystemClock
import android.provider.Settings
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.google.firebase.messaging.FirebaseMessaging
import com.mongs.wear.domain.common.client.MqttClient
import com.mongs.wear.domain.common.repository.AppRepository
import com.mongs.wear.presentation.common.BaseViewModel
import com.mongs.wear.worker.StepSensorManager
import com.mongs.wear.worker.StepsWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@SuppressLint("HardwareIds")
@HiltViewModel
class MainActivityViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val workerManager: WorkManager,
    private val mqttClient: MqttClient,
    private val appRepository: AppRepository,
    private val stepSensorManager: StepSensorManager,
    private val firebaseMessaging: FirebaseMessaging,
) : BaseViewModel() {

    init {

        /**
         * 네트워크 가용 여부 확인
         */
        viewModelScopeWithHandler.launch {
            val deviceBootedDt = LocalDateTime.now()
                .minusSeconds(TimeUnit.MILLISECONDS.toSeconds(SystemClock.uptimeMillis()))

            appRepository.setDeviceBootedDt(deviceBootedDt = deviceBootedDt)

            appRepository.setNetwork(network = isNetworkAvailable(context = context))

            appRepository.setDeviceId(deviceId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID))

            workerManager.enqueueUniquePeriodicWork(
                StepsWorker.WORKER_NAME,
                ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
                PeriodicWorkRequestBuilder<StepsWorker>(15, TimeUnit.MINUTES)
//                .setInitialDelay(15, TimeUnit.SECONDS)
//                .setConstraints(Constraints.Builder()
//                    .setRequiredNetworkType(NetworkType.CONNECTED)
//                    .build())
                    .build()
            )
        }
    }

    fun connectSensor() = CoroutineScope(Dispatchers.IO).launch {
        stepSensorManager.listen()
    }

    fun disconnectSensor() = CoroutineScope(Dispatchers.IO).launch {
        stepSensorManager.stop()
    }

    fun resumeConnectMqtt() = CoroutineScope(Dispatchers.IO).launch {
        this@MainActivityViewModel.mqttClient.resumeConnect()
    }

    fun pauseConnectMqtt() = CoroutineScope(Dispatchers.IO).launch {
        this@MainActivityViewModel.mqttClient.pauseConnect()
    }

    fun disconnectMqtt() = CoroutineScope(Dispatchers.IO).launch {
        this@MainActivityViewModel.mqttClient.disconnect()
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    override fun exceptionHandler(exception: Throwable) {}
}
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
import com.mongs.wear.presentation.common.viewModel.BaseViewModel
import com.mongs.wear.presentation.common.manager.StepSensorManager
import com.mongs.wear.presentation.common.worker.StepSensorWorker
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
    private val firebaseMessaging: FirebaseMessaging,
    private val stepSensorManager: StepSensorManager,
    private val workerManager: WorkManager,
    private val appRepository: AppRepository,
    private val mqttClient: MqttClient,
) : BaseViewModel() {

    init {
        /**
         * 네트 워크 가용 여부 확인
         */
        viewModelScopeWithHandler.launch {
            val deviceBootedDt = LocalDateTime.now()
                .minusSeconds(TimeUnit.MILLISECONDS.toSeconds(SystemClock.uptimeMillis()))

            appRepository.setDeviceBootedDt(deviceBootedDt = deviceBootedDt)

            appRepository.setNetwork(network = isNetworkAvailable())

            appRepository.setDeviceId(deviceId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID))

            // 15분 간격 걸음 수 서버 동기화 워커 실행
            workerManager.enqueueUniquePeriodicWork(
                StepSensorWorker.WORKER_NAME,
                ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,        // TODO: 워커 중복 실행 방지 플래그 변경
                PeriodicWorkRequestBuilder<StepSensorWorker>(15, TimeUnit.MINUTES)
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

    /**
     * 네트워크 연결 여부 확인 함수
     */
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = this.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    override fun exceptionHandler(exception: Throwable) {}
}
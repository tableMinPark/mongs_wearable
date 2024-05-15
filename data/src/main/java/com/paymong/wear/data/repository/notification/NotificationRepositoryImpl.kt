package com.paymong.wear.data.repository.notification

import com.paymong.wear.data.api.client.MqttApi
import com.paymong.wear.data.repository.notification.callback.CallbackRepository
import com.paymong.wear.data.repository.notification.callback.MessageCallback
import com.paymong.wear.data.repository.notification.callback.SubscribeCallback
import com.paymong.wear.domain.repository.common.DeviceRepository
import com.paymong.wear.domain.repository.notification.NotificationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val deviceRepository: DeviceRepository,
    private val mqttApi: MqttApi,
    private val callbackRepository: CallbackRepository
) : NotificationRepository {

    companion object {
        const val TOPIC = "mongs"
//        const val TOPIC = "dev_mongs"
    }

    override suspend fun initializeNotification(accountId: Long) {
        mqttApi.init(
            messageCallback = MessageCallback(callbackRepository),
            connectDisableCallback = {
                CoroutineScope(Dispatchers.IO).launch {
                    deviceRepository.setNetworkFlag(false)
                }
            },
            connectSuccessCallback = {
                CoroutineScope(Dispatchers.IO).launch {
                    deviceRepository.setNetworkFlag(true)
                }
                val subscribeCallback = SubscribeCallback(disconnect = { mqttApi.disConnect() })
                mqttApi.subscribe("$TOPIC/$accountId", subscribeCallback)
            }
        )
    }

    override suspend fun reconnectNotification() {
        mqttApi.connect()
    }

    override suspend fun disconnectNotification() {
        mqttApi.disConnect()
    }

    override suspend fun resetNotification() {
        mqttApi.reset()
    }
}
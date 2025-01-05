package com.mongs.wear.data.manager.consumer

import com.google.gson.Gson
import com.mongs.wear.data.manager.dto.response.GetMongResponseDto
import com.mongs.wear.data.manager.dto.response.MongResponseDto
import com.mongs.wear.data.manager.dto.response.MongStateResponseDto
import com.mongs.wear.data.manager.dto.response.MongStatusResponseDto
import com.mongs.wear.data.manager.resolver.ManagementObserveResolver
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ManagementConsumer  @Inject constructor(
    private val observeResolver: ManagementObserveResolver,
    private val gson: Gson,
) {

    companion object {
        private const val TAG = "ManagerConsumer"
        private const val MANAGER_MANAGEMENT_OBSERVE_MONG = "MANAGER-MANAGEMENT-200"
    }

    fun messageArrived(code: String, resultJson: String) {

        when (code) {
            MANAGER_MANAGEMENT_OBSERVE_MONG ->
                observeResolver.updateMong(
                    getMongResponseDto = gson.fromJson(
                        resultJson,
                        GetMongResponseDto::class.java
                    )
                )

            else -> {}
        }
    }
}